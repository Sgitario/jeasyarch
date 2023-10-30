package io.jeasyarch.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.jeasyarch.api.RunOnKubernetes;
import io.jeasyarch.api.clients.KubernetesClient;

@RunOnKubernetes
public class KubernetesServiceLifecycleIT extends ServiceLifecycleIT {

    @Inject
    static KubernetesClient clientAsStaticInstance;

    @Inject
    static io.fabric8.kubernetes.client.KubernetesClient fabric8KubernetesClient;

    @Test
    public void shouldInjectKubernetesClientsAsStaticInstance() {
        assertNotNull(clientAsStaticInstance);
        assertNotNull(fabric8KubernetesClient);
    }

    @Test
    public void shouldInjectKubernetesClientAsField(KubernetesClient clientAsField) {
        assertNotNull(clientAsField);
    }
}
