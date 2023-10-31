package io.jeasyarch.configuration;

public final class ContainerServiceConfiguration {
    private boolean privileged = false;
    private String image;

    public boolean isPrivileged() {
        return privileged;
    }

    public String getImage() {
        return image;
    }

    public void setPrivileged(boolean privileged) {
        this.privileged = privileged;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
