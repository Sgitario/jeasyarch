package io.jeasyarch.resources.gitremoteproject.local;

import org.apache.commons.lang3.StringUtils;

import io.jeasyarch.core.ServiceContext;
import io.jeasyarch.resources.common.local.GenericContainerManagedResource;
import io.jeasyarch.resources.gitremoteproject.GitRemoteProjectResource;
import io.jeasyarch.utils.PropertiesUtils;

public class GitRemoteProjectGenericContainerManagedResource extends GenericContainerManagedResource {

    private final String repo;
    private final String branch;
    private final String contextDir;
    private final String[] buildCommands;
    private final String dockerfile;

    private GitRemoteProjectResource resource;

    public GitRemoteProjectGenericContainerManagedResource(String repo, String branch, String contextDir,
            String[] buildCommands, String dockerfile, String expectedLog, String[] command, int[] ports) {
        super(expectedLog, command, ports);
        this.repo = PropertiesUtils.resolveProperty(repo);
        this.branch = PropertiesUtils.resolveProperty(branch);
        this.contextDir = PropertiesUtils.resolveProperty(contextDir);
        this.buildCommands = PropertiesUtils.resolveProperties(buildCommands);
        this.dockerfile = PropertiesUtils.resolveProperty(dockerfile);
    }

    @Override
    public String getDisplayName() {
        String name = repo;
        if (StringUtils.isNotEmpty(branch)) {
            name += "/" + branch;
        }

        if (StringUtils.isNotEmpty(contextDir)) {
            name += "/" + contextDir;
        }

        return "Git project from " + name;
    }

    @Override
    protected void init(ServiceContext context) {
        super.init(context);
        this.resource = new GitRemoteProjectResource(context, repo, branch, contextDir, buildCommands, dockerfile);
    }

    @Override
    protected String getImage() {
        return resource.getGeneratedImage();
    }
}
