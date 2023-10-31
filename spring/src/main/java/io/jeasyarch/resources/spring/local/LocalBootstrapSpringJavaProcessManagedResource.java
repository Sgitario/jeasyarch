package io.jeasyarch.resources.spring.local;

import static io.jeasyarch.utils.SpringUtils.MANAGEMENT_HTTP_PORT;
import static io.jeasyarch.utils.SpringUtils.MANAGEMENT_HTTP_PORT_DEFAULT;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import io.jeasyarch.core.ServiceContext;
import io.jeasyarch.resources.local.JavaProcessManagedResource;
import io.jeasyarch.resources.spring.common.SpringResource;
import io.jeasyarch.utils.Ports;
import io.jeasyarch.utils.SpringUtils;

public class LocalBootstrapSpringJavaProcessManagedResource extends JavaProcessManagedResource {

    private final String location;
    private final boolean forceBuild;
    private final String[] buildCommands;

    private SpringResource resource;

    public LocalBootstrapSpringJavaProcessManagedResource(String location, boolean forceBuild, String[] buildCommands) {
        this.location = location;
        this.forceBuild = forceBuild;
        this.buildCommands = buildCommands;
    }

    @Override
    public String getDisplayName() {
        return resource.getDisplayName();
    }

    @Override
    protected String getHttpPortProperty() {
        return SpringUtils.SERVER_HTTP_PORT;
    }

    @Override
    protected Path getRunner() {
        return resource.getRunner();
    }

    @Override
    public boolean isRunning() {
        return super.isRunning() && resource.isRunning(getLoggingHandler());
    }

    @Override
    public boolean isFailed() {
        return super.isFailed() || resource.isFailed(getLoggingHandler());
    }

    @Override
    protected void init(ServiceContext context) {
        super.init(context);
        resource = new SpringResource(context, location, forceBuild, buildCommands);
    }

    @Override
    protected Map<Integer, Integer> assignCustomPorts() {
        Map<Integer, Integer> customPorts = new HashMap<>();
        int assignedSslPort = getOrAssignPortByProperty(MANAGEMENT_HTTP_PORT);
        Ports.SSL_PORTS.forEach(sslPort -> customPorts.put(MANAGEMENT_HTTP_PORT_DEFAULT, assignedSslPort));
        propertiesToOverwrite.put(MANAGEMENT_HTTP_PORT, "" + assignedSslPort);
        return customPorts;
    }
}
