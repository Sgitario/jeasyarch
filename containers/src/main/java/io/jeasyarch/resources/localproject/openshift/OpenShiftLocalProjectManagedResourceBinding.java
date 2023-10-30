package io.jeasyarch.resources.localproject.openshift;

import io.jeasyarch.api.LocalProject;
import io.jeasyarch.api.Service;
import io.jeasyarch.api.extensions.LocalProjectManagedResourceBinding;
import io.jeasyarch.core.JEasyArchContext;
import io.jeasyarch.core.ManagedResource;
import io.jeasyarch.core.extensions.OpenShiftExtensionBootstrap;

public class OpenShiftLocalProjectManagedResourceBinding implements LocalProjectManagedResourceBinding {
    @Override
    public boolean appliesFor(JEasyArchContext context) {
        return OpenShiftExtensionBootstrap.isEnabled(context);
    }

    @Override
    public ManagedResource init(JEasyArchContext context, Service service, LocalProject metadata) {
        return new OpenShiftLocalProjectManagedResource(metadata.location(), metadata.buildCommands(),
                metadata.dockerfile(), metadata.expectedLog(), metadata.command(), metadata.ports());
    }
}
