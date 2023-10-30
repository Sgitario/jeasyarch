package io.jeasyarch.configuration;

public final class DockerServiceConfiguration {
    private boolean privileged = false;

    public boolean isPrivileged() {
        return privileged;
    }

    public void setPrivileged(boolean privileged) {
        this.privileged = privileged;
    }
}
