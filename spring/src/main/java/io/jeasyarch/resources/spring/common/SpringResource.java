package io.jeasyarch.resources.spring.common;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import io.jeasyarch.configuration.SpringServiceConfiguration;
import io.jeasyarch.configuration.SpringServiceConfigurationBuilder;
import io.jeasyarch.core.ServiceContext;
import io.jeasyarch.logging.LoggingHandler;
import io.jeasyarch.utils.Command;
import io.jeasyarch.utils.FileUtils;
import io.jeasyarch.utils.OutputUtils;
import io.jeasyarch.utils.PropertiesUtils;

public class SpringResource {
    private static final List<String> ERRORS = List.of("Application run failed");
    private static final String SOURCES = "-sources";
    private static final String JVM_RUNNER = ".jar";

    private final ServiceContext context;
    private final Path location;
    private final Path runner;
    private final String[] buildCommands;

    public SpringResource(ServiceContext context, String location, boolean forceBuild, String[] buildCommands) {
        this.context = context;
        this.location = Path.of(location);
        if (!Files.exists(this.location)) {
            throw new RuntimeException("Spring location does not exist.");
        }

        this.buildCommands = PropertiesUtils.resolveProperties(buildCommands);
        this.context.loadCustomConfiguration(SpringServiceConfiguration.class, new SpringServiceConfigurationBuilder());
        if (forceBuild) {
            this.runner = tryToBuildRunner();
        } else {
            this.runner = findRunner().map(Path::of).orElseGet(this::tryToBuildRunner);
        }

    }

    public String getDisplayName() {
        return "Spring Boot";
    }

    public Path getRunner() {
        return runner;
    }

    public boolean isRunning(LoggingHandler loggingHandler) {
        return loggingHandler != null && loggingHandler.logsContains(getExpectedLog());
    }

    public boolean isFailed(LoggingHandler loggingHandler) {
        return loggingHandler != null && ERRORS.stream().anyMatch(loggingHandler::logsContains);
    }

    public String getExpectedLog() {
        return context.getConfigurationAs(SpringServiceConfiguration.class).getExpectedLog();
    }

    private Optional<String> findRunner() {
        Path target = OutputUtils.target();
        if (target.endsWith(OutputUtils.BUILD.toString())) {
            target = target.resolve("libs");
        }

        return FileUtils.findFile(location.resolve(target),
                f -> f.endsWith(JVM_RUNNER) && !f.endsWith(SOURCES + JVM_RUNNER));
    }

    private Path tryToBuildRunner() {
        FileUtils.copyDirectoryTo(location, context.getServiceFolder());
        FileUtils.deletePath(context.getServiceFolder().resolve(OutputUtils.target()));
        FileUtils.deletePath(context.getServiceFolder().resolve("src").resolve("test").resolve("java"));
        if (buildCommands.length > 0) {
            List<String> effectiveCommands = new ArrayList<>();
            effectiveCommands.addAll(Arrays.asList(buildCommands));
            effectiveCommands.addAll(Arrays.asList("-DskipTests", "-Dformatter.skip", "-Dcheckstyle.skip"));
            try {
                new Command(effectiveCommands).onDirectory(context.getServiceFolder().toString()).runAndWait();
            } catch (Exception ex) {
                throw new RuntimeException("Error running build commands for service " + context.getName(), ex);
            }
        }

        return FileUtils.findFile(context.getServiceFolder().resolve(OutputUtils.target()), JVM_RUNNER).map(Path::of)
                .orElseThrow(() -> new RuntimeException(
                        "Could not locate the Spring JAR file. You need to build the application before running the test."));
    }
}
