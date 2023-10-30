package io.jeasyarch.resources.gitremoteproject;

import java.lang.annotation.Annotation;
import java.util.ServiceLoader;

import io.jeasyarch.api.GitRemoteProject;
import io.jeasyarch.api.Service;
import io.jeasyarch.api.extensions.AnnotationBinding;
import io.jeasyarch.api.extensions.GitRemoteProjectManagedResourceBinding;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;
import io.jeasyarch.resources.gitremoteproject.local.DockerGitRemoteProjectManagedResource;

public class GitRemoteProjectAnnotationBinding implements AnnotationBinding {

    private final ServiceLoader<GitRemoteProjectManagedResourceBinding> bindings = ServiceLoader
            .load(GitRemoteProjectManagedResourceBinding.class);

    @Override
    public boolean isFor(Annotation... annotations) {
        return findAnnotation(annotations, GitRemoteProject.class).isPresent();
    }

    @Override
    public ManagedResource getManagedResource(JEasyArchContext context, Service service, Annotation... annotations) {
        GitRemoteProject metadata = findAnnotation(annotations, GitRemoteProject.class).get();

        for (GitRemoteProjectManagedResourceBinding binding : bindings) {
            if (binding.appliesFor(context)) {
                return binding.init(context, service, metadata);
            }
        }

        // If none handler found, then the container will be running on localhost by default
        return new DockerGitRemoteProjectManagedResource(metadata.repo(), metadata.branch(), metadata.contextDir(),
                metadata.buildCommands(), metadata.dockerfile(), metadata.expectedLog(), metadata.command(),
                metadata.ports());
    }

}
