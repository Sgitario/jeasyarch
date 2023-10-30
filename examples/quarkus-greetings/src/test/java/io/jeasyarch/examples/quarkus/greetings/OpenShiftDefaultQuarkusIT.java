package io.jeasyarch.examples.quarkus.greetings;

import io.jeasyarch.api.OpenShiftServiceConfiguration;
import io.jeasyarch.api.RunOnOpenShift;

@RunOnOpenShift
@OpenShiftServiceConfiguration(forService = "quarkus", useRoute = false)
public class OpenShiftDefaultQuarkusIT extends DefaultQuarkusIT {
}
