package io.jeasyarch.utils;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import io.fabric8.kubernetes.api.model.ConfigMapVolumeSourceBuilder;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.KubernetesList;
import io.fabric8.kubernetes.api.model.LabelSelector;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.SecretVolumeSourceBuilder;
import io.fabric8.kubernetes.api.model.Volume;
import io.fabric8.kubernetes.api.model.VolumeBuilder;
import io.fabric8.kubernetes.api.model.VolumeMountBuilder;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.utils.Serialization;
import io.jeasyarch.api.Service;
import io.jeasyarch.api.clients.BaseKubernetesClient;

public final class ManifestsUtils {

    public static final String LABEL_TO_WATCH_FOR_LOGS = "tsLogWatch";
    public static final String LABEL_CONTEXT_ID = "jeasyArchId";
    public static final String LABEL_DEPLOYMENT = "deployment";

    private static final String RESOURCE_MNT_FOLDER = "/resource";

    private ManifestsUtils() {

    }

    public static void writeFile(Path target, HasMetadata... objects) {
        Object value = null;
        if (objects.length > 1) {
            KubernetesList list = new KubernetesList();
            list.setItems(Arrays.asList(objects));
            value = list;
        } else {
            value = objects[0];
        }

        try {
            FileOutputStream os = new FileOutputStream(target.toFile());
            Serialization.yamlMapper().writeValue(os, value);
        } catch (IOException e) {
            fail("Failed adding properties into template. Caused by " + e.getMessage());
        }
    }

    public static void enrichDeployment(BaseKubernetesClient client, Deployment deployment, Service service) {
        enrichDeployment(client, deployment, service, Collections.emptyMap());
    }

    public static void enrichDeployment(BaseKubernetesClient client, Deployment deployment, Service service,
            Map<String, String> extraTemplateProperties) {
        if (deployment.getMetadata() == null) {
            deployment.setMetadata(new ObjectMeta());
        }

        // set namespace
        deployment.getMetadata().setNamespace(client.namespace());
        Map<String, String> objMetadataLabels = Optional.ofNullable(deployment.getMetadata().getLabels())
                .orElse(new HashMap<>());

        objMetadataLabels.put(LABEL_DEPLOYMENT, service.getName());
        objMetadataLabels.put(LABEL_CONTEXT_ID, service.getContextId());
        deployment.getMetadata().setLabels(objMetadataLabels);

        // set deployment name
        deployment.getMetadata().setName(service.getName());

        // set metadata to template
        if (deployment.getSpec().getTemplate().getMetadata() == null) {
            deployment.getSpec().getTemplate().setMetadata(new ObjectMeta());
        }
        deployment.getSpec().getTemplate().getMetadata().setNamespace(client.namespace());

        // add selector
        if (deployment.getSpec().getSelector() == null) {
            deployment.getSpec().setSelector(new LabelSelector());
        }
        if (deployment.getSpec().getSelector().getMatchLabels() == null) {
            deployment.getSpec().getSelector().setMatchLabels(new HashMap<>());
        }
        deployment.getSpec().getSelector().getMatchLabels().put(LABEL_DEPLOYMENT, service.getName());

        // add labels
        if (deployment.getSpec().getTemplate().getMetadata().getLabels() == null) {
            deployment.getSpec().getTemplate().getMetadata().setLabels(new HashMap<>());
        }
        Map<String, String> templateMetadataLabels = deployment.getSpec().getTemplate().getMetadata().getLabels();
        templateMetadataLabels.put(LABEL_DEPLOYMENT, service.getName());
        templateMetadataLabels.put(LABEL_TO_WATCH_FOR_LOGS, service.getName());
        templateMetadataLabels.put(LABEL_CONTEXT_ID, service.getContextId());

        // add env var properties
        Map<String, String> enrichProperties = enrichProperties(client, service.getProperties(), deployment);
        enrichProperties.putAll(extraTemplateProperties);
        deployment.getSpec().getTemplate().getSpec().getContainers()
                .forEach(container -> enrichProperties.entrySet().forEach(property -> {
                    String key = property.getKey();
                    EnvVar envVar = getEnvVarByKey(key, container);
                    if (envVar == null) {
                        container.getEnv().add(new EnvVar(key, property.getValue(), null));
                    } else {
                        envVar.setValue(property.getValue());
                    }
                }));
    }

    private static Map<String, String> enrichProperties(BaseKubernetesClient client, Map<String, String> properties,
            Deployment deployment) {
        // mount path x volume
        Map<String, Volume> volumes = new HashMap<>();

        Map<String, String> output = new HashMap<>();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            String value = entry.getValue();
            if (isResource(entry.getValue())) {
                String path = entry.getValue().replace(PropertiesUtils.RESOURCE_PREFIX, StringUtils.EMPTY);
                String mountPath = getMountPath(path);
                String filename = getFileName(path);
                String configMapName = normalizeName(mountPath);

                // Update config map
                client.createOrUpdateConfigMap(configMapName, filename, getFileContent(path));

                // Add the volume
                if (!volumes.containsKey(mountPath)) {
                    Volume volume = new VolumeBuilder().withName(configMapName)
                            .withConfigMap(new ConfigMapVolumeSourceBuilder().withName(configMapName).build()).build();
                    volumes.put(mountPath, volume);
                }

                value = mountPath + PropertiesUtils.SLASH + filename;
            } else if (isSecret(entry.getValue())) {
                String path = entry.getValue().replace(PropertiesUtils.SECRET_PREFIX, StringUtils.EMPTY);
                String mountPath = getMountPath(path);
                String filename = getFileName(path);
                String secretName = normalizeName(path);

                // Push secret file
                client.createSecretFromFile(secretName, getFilePath(path));

                // Add the volume
                Volume volume = new VolumeBuilder().withName(secretName)
                        .withSecret(new SecretVolumeSourceBuilder().withSecretName(secretName).build()).build();
                volumes.put(mountPath, volume);

                value = mountPath + PropertiesUtils.SLASH + filename;
            }

            output.put(entry.getKey(), value);
        }

        for (Map.Entry<String, Volume> volume : volumes.entrySet()) {
            deployment.getSpec().getTemplate().getSpec().getVolumes().add(volume.getValue());

            // Configure all the containers to map the volume
            deployment.getSpec().getTemplate().getSpec().getContainers()
                    .forEach(container -> container.getVolumeMounts()
                            .add(new VolumeMountBuilder().withName(volume.getValue().getName()).withReadOnly(true)
                                    .withMountPath(volume.getKey()).build()));
        }

        return output;
    }

    private static EnvVar getEnvVarByKey(String key, Container container) {
        return container.getEnv().stream().filter(env -> StringUtils.equals(key, env.getName())).findFirst()
                .orElse(null);
    }

    private static String getMountPath(String path) {
        if (!path.contains(PropertiesUtils.SLASH)) {
            return RESOURCE_MNT_FOLDER;
        }

        String mountPath = StringUtils.defaultIfEmpty(path.substring(0, path.lastIndexOf(PropertiesUtils.SLASH)),
                RESOURCE_MNT_FOLDER);
        if (!path.startsWith(PropertiesUtils.SLASH)) {
            mountPath = PropertiesUtils.SLASH + mountPath;
        }

        return mountPath;
    }

    private static String getFileName(String path) {
        if (!path.contains(PropertiesUtils.SLASH)) {
            return path;
        }

        return path.substring(path.lastIndexOf(PropertiesUtils.SLASH) + 1);
    }

    private static String getFileContent(String path) {
        String filePath = getFilePath(path);
        if (Files.exists(Path.of(filePath))) {
            // from file system
            return FileUtils.loadFile(Path.of(filePath).toFile());
        }

        // from classpath
        return FileUtils.loadFile(filePath);
    }

    private static String getFilePath(String path) {
        try (Stream<Path> binariesFound = Files.find(PropertiesUtils.TARGET, Integer.MAX_VALUE,
                (file, basicFileAttributes) -> file.toString().contains(path))) {
            return binariesFound.map(Path::toString).findFirst().orElse(path);
        } catch (IOException ex) {
            // ignored
        }

        return path;
    }

    private static boolean isResource(String key) {
        return key.startsWith(PropertiesUtils.RESOURCE_PREFIX);
    }

    private static boolean isSecret(String key) {
        return key.startsWith(PropertiesUtils.SECRET_PREFIX);
    }

    private static String normalizeName(String name) {
        return StringUtils.removeStart(name, PropertiesUtils.SLASH).replaceAll(Pattern.quote("."), "-")
                .replaceAll(PropertiesUtils.SLASH, "-");
    }
}
