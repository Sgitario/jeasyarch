package io.jeasyarch.resources.localproject;

import io.jeasyarch.core.ServiceContext;
import io.jeasyarch.utils.Command;
import io.jeasyarch.utils.ContainerUtils;

public class LocalProjectResource {

    private String generatedImage;

    public LocalProjectResource(ServiceContext context, String location, String[] buildCommands, String dockerfile) {
        if (buildCommands.length > 0) {
            try {
                new Command(buildCommands).onDirectory(location).runAndWait();
            } catch (Exception ex) {
                throw new RuntimeException("Error running build commands for service " + context.getName(), ex);
            }
        }

        // generate image
        generatedImage = ContainerUtils.build(context, dockerfile, location);
    }

    public String getGeneratedImage() {
        return generatedImage;
    }
}
