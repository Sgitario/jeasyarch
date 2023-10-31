package io.jeasyarch.resources.containers.local;

import org.apache.commons.lang3.StringUtils;

import io.jeasyarch.resources.common.local.GenericContainerManagedResource;
import io.jeasyarch.utils.PropertiesUtils;

public class DefaultGenericContainerManagedResource extends GenericContainerManagedResource {

    private final String image;

    public DefaultGenericContainerManagedResource(String image, String expectedLog, String[] command, int[] ports) {
        super(expectedLog, command, ports);
        this.image = PropertiesUtils.resolveProperty(image);
    }

    @Override
    public String getDisplayName() {
        return image;
    }

    @Override
    protected String getImage() {
        return StringUtils.defaultIfBlank(super.getImage(), image);
    }
}
