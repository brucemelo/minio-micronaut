package com.brucemelo;


import io.micronaut.testresources.testcontainers.AbstractTestContainersProvider;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MinioTestResourceProvider extends AbstractTestContainersProvider<MinIOContainer> {

    public static final String MINIO_USERNAME = "minio.access-key";
    public static final String MINIO_PASSWORD = "minio.secret-key";
    public static final String MINIO_URL = "minio.url";
    public static final List<String> RESOLVABLE_PROPERTIES = List.of(
            MINIO_USERNAME,
            MINIO_PASSWORD,
            MINIO_URL
    );

    @Override
    protected String getSimpleName() {
        return "MinIO";
    }

    @Override
    protected String getDefaultImageName() {
        return "minio/minio";
    }

    @Override
    protected MinIOContainer createContainer(DockerImageName imageName, Map<String, Object> requestedProperties, Map<String, Object> testResourcesConfig) {
        return new MinIOContainer("minio/minio:RELEASE.2023-09-04T19-57-37Z");
    }

    @Override
    protected Optional<String> resolveProperty(String propertyName, MinIOContainer container) {
        if (MINIO_URL.equals(propertyName)) {
            return Optional.of(container.getS3URL());
        }
        if (MINIO_USERNAME.equals(propertyName)) {
            return Optional.of(container.getUserName());
        }
        if (MINIO_PASSWORD.equals(propertyName)) {
            return Optional.of(container.getPassword());
        }
        return Optional.empty();
    }

    @Override
    public List<String> getResolvableProperties(Map<String, Collection<String>> propertyEntries, Map<String, Object> testResourcesConfig) {
        return RESOLVABLE_PROPERTIES;
    }

}
