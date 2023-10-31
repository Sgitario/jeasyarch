package io.jeasyarch.resources.spring.kubernetes;

import io.jeasyarch.core.ServiceContext;
import io.jeasyarch.resources.kubernetes.KubernetesManagedResource;
import io.jeasyarch.resources.spring.common.SpringResource;
import io.jeasyarch.utils.ContainerUtils;
import io.jeasyarch.utils.SpringUtils;

public class ContainerRegistrySpringKubernetesManagedResource extends KubernetesManagedResource {

    private final String location;
    private final boolean forceBuild;
    private final String[] buildCommands;

    private SpringResource resource;
    private String image;

    public ContainerRegistrySpringKubernetesManagedResource(String location, boolean forceBuild,
            String[] buildCommands) {
        this.location = location;
        this.forceBuild = forceBuild;
        this.buildCommands = buildCommands;
    }

    @Override
    public String getDisplayName() {
        return resource.getDisplayName();
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    protected String getExpectedLog() {
        return resource.getExpectedLog();
    }

    @Override
    protected int[] getPorts() {
        return new int[] { context.getOwner().getProperty(SpringUtils.SERVER_HTTP_PORT).map(Integer::parseInt)
                .orElse(SpringUtils.HTTP_PORT_DEFAULT) };
    }

    @Override
    protected void init(ServiceContext context) {
        super.init(context);

        resource = new SpringResource(context, location, forceBuild, buildCommands);
        image = createImageAndPush();
    }

    private String createImageAndPush() {
        return ContainerUtils.createImageAndPush(context, resource.getRunner());
    }

}
