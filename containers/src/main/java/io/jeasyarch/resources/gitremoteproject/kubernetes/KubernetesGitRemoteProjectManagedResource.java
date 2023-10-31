package io.jeasyarch.resources.gitremoteproject.kubernetes;

import io.jeasyarch.core.ServiceContext;
import io.jeasyarch.resources.gitremoteproject.GitRemoteProjectResource;
import io.jeasyarch.resources.kubernetes.KubernetesManagedResource;
import io.jeasyarch.utils.ContainerUtils;
import io.jeasyarch.utils.PropertiesUtils;

public class KubernetesGitRemoteProjectManagedResource extends KubernetesManagedResource {

    private final String repo;
    private final String branch;
    private final String contextDir;
    private final String[] buildCommands;
    private final String dockerfile;
    private final String expectedLog;
    private final String[] command;
    private final int[] ports;

    private GitRemoteProjectResource resource;

    public KubernetesGitRemoteProjectManagedResource(String repo, String branch, String contextDir,
            String[] buildCommands, String dockerfile, String expectedLog, String[] command, int[] ports) {
        this.repo = PropertiesUtils.resolveProperty(repo);
        this.branch = PropertiesUtils.resolveProperty(branch);
        this.contextDir = PropertiesUtils.resolveProperty(contextDir);
        this.buildCommands = PropertiesUtils.resolveProperties(buildCommands);
        this.dockerfile = PropertiesUtils.resolveProperty(dockerfile);
        this.command = PropertiesUtils.resolveProperties(command);
        this.expectedLog = PropertiesUtils.resolveProperty(expectedLog);
        this.ports = ports;
    }

    @Override
    protected void init(ServiceContext context) {
        super.init(context);
        this.resource = new GitRemoteProjectResource(context, repo, branch, contextDir, buildCommands, dockerfile);
        ContainerUtils.push(context);
    }

    @Override
    protected String getImage() {
        return resource.getGeneratedImage();
    }

    @Override
    protected String getExpectedLog() {
        return expectedLog;
    }

    @Override
    protected String[] getCommand() {
        return command;
    }

    @Override
    protected int[] getPorts() {
        return ports;
    }
}
