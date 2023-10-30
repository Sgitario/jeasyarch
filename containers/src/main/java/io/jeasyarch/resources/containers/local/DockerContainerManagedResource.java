package io.jeasyarch.resources.containers.local;

import io.jeasyarch.resources.common.local.GenericContainerManagedResource;
import io.jeasyarch.utils.PropertiesUtils;

public class DockerContainerManagedResource extends GenericContainerManagedResource {

    private final String image;

    public DockerContainerManagedResource(String image, String expectedLog, String[] command, int[] ports) {
        super(expectedLog, command, ports);
        this.image = PropertiesUtils.resolveProperty(image);
    }

    @Override
    public String getDisplayName() {
        return image;
    }

    @Override
    protected String getImage() {
        return image;
    }
}
