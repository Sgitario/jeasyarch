package io.jeasyarch.resources.quarkus.common;

import java.nio.file.Path;
import java.nio.file.Paths;

import io.jeasyarch.utils.ReflectionUtils;
import io.quarkus.builder.BuildContext;
import io.quarkus.builder.BuildStep;
import io.quarkus.builder.item.BuildItem;

// needs to be in a class of it's own in order to avoid java.lang.IncompatibleClassChangeError
public class KubernetesCustomProjectBuildStep implements BuildStep {

    private static final String FILE_PROJECT_FACTORY_CLASS = "io.dekorate.project.FileProjectFactory";
    private final Class<?> buildItemClass;

    public KubernetesCustomProjectBuildStep(Class<?> buildItemClass) {
        this.buildItemClass = buildItemClass;
    }

    @Override
    public void execute(BuildContext context) {
        Path modulePath = Paths.get("").toAbsolutePath();

        // super hack to let Dekorate do things right
        ClassLoader cl = this.getClass().getClassLoader();
        try {
            Class<?> fileProjectClass = cl.loadClass(FILE_PROJECT_FACTORY_CLASS);
            ReflectionUtils.invokeStaticMethod(fileProjectClass, "create", modulePath.toFile());
        } catch (ClassNotFoundException ignored) {

        }

        // produce the custom project that should be enough if Dekorate works fine
        context.produce((BuildItem) ReflectionUtils.createInstance(buildItemClass, modulePath));
    }
}
