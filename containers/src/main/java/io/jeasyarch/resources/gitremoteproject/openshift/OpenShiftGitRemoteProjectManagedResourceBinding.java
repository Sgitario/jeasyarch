package io.jeasyarch.resources.gitremoteproject.openshift;

import io.jeasyarch.api.GitRemoteProject;
import io.jeasyarch.api.Service;
import io.jeasyarch.api.extensions.GitRemoteProjectManagedResourceBinding;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;
import io.jeasyarch.core.extensions.OpenShiftExtensionBootstrap;

public class OpenShiftGitRemoteProjectManagedResourceBinding implements GitRemoteProjectManagedResourceBinding {
    @Override
    public boolean appliesFor(JEasyArchContext context) {
        return OpenShiftExtensionBootstrap.isEnabled(context);
    }

    @Override
    public ManagedResource init(JEasyArchContext context, Service service, GitRemoteProject metadata) {
        return new OpenShiftGitRemoteProjectManagedResource(metadata.repo(), metadata.branch(), metadata.contextDir(),
                metadata.buildCommands(), metadata.dockerfile(), metadata.expectedLog(), metadata.command(),
                metadata.ports());
    }
}
