package com.brucemelo;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import io.micronaut.serde.annotation.SerdeImport;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;

@Factory
@SerdeImport(value = ObjectWriteResponse.class)
public class MinioConfig {

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.accessKey}")
    private String minioAccessKey;

    @Value("${minio.secretKey}")
    private String minioSecretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                        .endpoint(minioUrl)
                        .credentials(minioAccessKey, minioSecretKey)
                        .build();
    }

}
