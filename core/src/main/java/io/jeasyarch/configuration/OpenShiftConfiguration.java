package io.jeasyarch.configuration;

public final class OpenShiftConfiguration {
    private boolean printInfoOnError = true;
    private boolean deleteProjectAfterAll = true;
    private boolean ephemeralProjectEnabled = true;
    private String[] additionalResources;

    public boolean isPrintInfoOnError() {
        return printInfoOnError;
    }

    public void setPrintInfoOnError(boolean printInfoOnError) {
        this.printInfoOnError = printInfoOnError;
    }

    public boolean isDeleteProjectAfterAll() {
        return deleteProjectAfterAll;
    }

    public void setDeleteProjectAfterAll(boolean deleteProjectAfterAll) {
        this.deleteProjectAfterAll = deleteProjectAfterAll;
    }

    public boolean isEphemeralProjectEnabled() {
        return ephemeralProjectEnabled;
    }

    public void setEphemeralProjectEnabled(boolean ephemeralProjectEnabled) {
        this.ephemeralProjectEnabled = ephemeralProjectEnabled;
    }

    public String[] getAdditionalResources() {
        return additionalResources;
    }

    public void setAdditionalResources(String[] additionalResources) {
        this.additionalResources = additionalResources;
    }
}
