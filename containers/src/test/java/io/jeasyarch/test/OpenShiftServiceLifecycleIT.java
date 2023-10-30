package io.jeasyarch.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.fabric8.openshift.client.OpenShiftClient;
import io.jeasyarch.api.RunOnOpenShift;
import io.jeasyarch.api.clients.OpenshiftClient;

@RunOnOpenShift
public class OpenShiftServiceLifecycleIT extends ServiceLifecycleIT {

    @Inject
    static OpenshiftClient clientAsStaticInstance;

    @Inject
    static OpenShiftClient fabric8OpenShiftClient;

    @Test
    public void shouldInjectOpenShiftClientsAsStaticInstance() {
        assertNotNull(clientAsStaticInstance);
        assertNotNull(fabric8OpenShiftClient);
    }

    @Test
    public void shouldInjectOpenShiftClientAsField(OpenshiftClient clientAsField) {
        assertNotNull(clientAsField);
    }
}
