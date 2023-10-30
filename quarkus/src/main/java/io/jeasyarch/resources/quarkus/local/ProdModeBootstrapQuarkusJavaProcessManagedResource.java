package io.jeasyarch.resources.quarkus.local;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import io.jeasyarch.api.Dependency;
import io.jeasyarch.core.ServiceContext;
import io.jeasyarch.resources.local.JavaProcessManagedResource;
import io.jeasyarch.resources.quarkus.common.BootstrapQuarkusResource;
import io.jeasyarch.utils.Ports;
import io.jeasyarch.utils.QuarkusUtils;
import io.jeasyarch.utils.SocketUtils;

public class ProdModeBootstrapQuarkusJavaProcessManagedResource extends JavaProcessManagedResource {

    private final String location;
    private final Class<?>[] classes;
    private final Dependency[] forcedDependencies;
    private final boolean forceBuild;
    private final String version;

    private BootstrapQuarkusResource resource;

    public ProdModeBootstrapQuarkusJavaProcessManagedResource(String location, Class<?>[] classes,
            Dependency[] forcedDependencies, boolean forceBuild, String version) {
        this.location = location;
        this.classes = classes;
        this.forcedDependencies = forcedDependencies;
        this.forceBuild = forceBuild;
        this.version = version;
    }

    @Override
    public String getDisplayName() {
        return resource.getDisplayName();
    }

    @Override
    protected String getHttpPortProperty() {
        return QuarkusUtils.QUARKUS_HTTP_PORT_PROPERTY;
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
        resource = new BootstrapQuarkusResource(context, location, classes, forcedDependencies, forceBuild, version);
    }

    @Override
    protected Map<Integer, Integer> assignCustomPorts() {
        Map<Integer, Integer> customPorts = new HashMap<>();

        // ssl port
        if (isSslEnabled()) {
            int assignedSslPort = getOrAssignPortByProperty(QuarkusUtils.QUARKUS_SSL_PORT_PROPERTY);
            Ports.SSL_PORTS.forEach(sslPort -> customPorts.put(Ports.DEFAULT_SSL_PORT, assignedSslPort));
            propertiesToOverwrite.put(QuarkusUtils.QUARKUS_SSL_PORT_PROPERTY, "" + assignedSslPort);
        }

        // grpc port
        String grpcPort = getProperty(QuarkusUtils.QUARKUS_GRPC_SERVER_PORT);
        if (grpcPort != null) {
            int assignedGrpcPort = SocketUtils.findAvailablePort(context.getOwner());
            customPorts.put(Integer.parseInt(grpcPort), assignedGrpcPort);
            propertiesToOverwrite.put(QuarkusUtils.QUARKUS_GRPC_SERVER_PORT, "" + assignedGrpcPort);
        }

        return customPorts;
    }

    private boolean isSslEnabled() {
        return getAllComputedProperties().keySet().stream().anyMatch(p -> p.contains("quarkus.http.ssl"));
    }
}
