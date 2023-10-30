package io.jeasyarch.api.model;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Kind;
import io.fabric8.kubernetes.model.annotation.Version;

@Version("v1beta2")
@Group("kafka.strimzi.io")
@Kind("Kafka")
public class KafkaInstanceCustomResource extends CustomResource<CustomResourceSpec, CustomResourceStatus>
        implements Namespaced {
}
