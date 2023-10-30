package io.jeasyarch.resources.gitremoteproject.kubernetes;

import io.jeasyarch.api.GitRemoteProject;
import io.jeasyarch.api.Service;
import io.jeasyarch.api.extensions.GitRemoteProjectManagedResourceBinding;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;
import io.jeasyarch.core.extensions.KubernetesExtensionBootstrap;

public class KubernetesGitRemoteProjectManagedResourceBinding implements GitRemoteProjectManagedResourceBinding {
    @Override
    public boolean appliesFor(JEasyArchContext context) {
        return KubernetesExtensionBootstrap.isEnabled(context);
    }

    @Override
    public ManagedResource init(JEasyArchContext context, Service service, GitRemoteProject metadata) {
        return new KubernetesGitRemoteProjectManagedResource(metadata.repo(), metadata.branch(), metadata.contextDir(),
                metadata.buildCommands(), metadata.dockerfile(), metadata.expectedLog(), metadata.command(),
                metadata.ports());
    }
}
