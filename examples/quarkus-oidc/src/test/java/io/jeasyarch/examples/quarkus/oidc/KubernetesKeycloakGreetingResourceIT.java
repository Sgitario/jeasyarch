package io.jeasyarch.examples.quarkus.oidc;

import io.jeasyarch.api.RunOnKubernetes;

@RunOnKubernetes
public class KubernetesKeycloakGreetingResourceIT extends KeycloakGreetingResourceIT {

    @Override
    protected String getRealmUrl() {
        return String.format("http://keycloak:8080/auth/realms/%s", REALM);
    }
}
