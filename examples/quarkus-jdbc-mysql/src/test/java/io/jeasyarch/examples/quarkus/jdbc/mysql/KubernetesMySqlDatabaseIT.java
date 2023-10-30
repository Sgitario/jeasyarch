package io.jeasyarch.examples.quarkus.jdbc.mysql;

import io.jeasyarch.api.KubernetesServiceConfiguration;
import io.jeasyarch.api.RunOnKubernetes;

@RunOnKubernetes
@KubernetesServiceConfiguration(forService = "database", useInternalService = true)
public class KubernetesMySqlDatabaseIT extends MySqlDatabaseIT {
}
