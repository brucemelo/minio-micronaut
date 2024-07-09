package com.brucemelo;

import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import jakarta.inject.Singleton;

import java.io.InputStream;

@Singleton
public record MinioService(MinioClient minioClient) {

    private static final int MIN_PART_SIZE = 5 * 1024 * 1024;
    private static final int UNKNOWN_OBJECT_SIZE = -1;

    public void makeBucket() {
        try {
            minioClient.makeBucket(
                    MakeBucketArgs
                            .builder()
                            .bucket("my-pdfs")
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectWriteResponse putObject(InputStream inputStream) {
        try (inputStream) {
            var putObjectArgs = PutObjectArgs.builder()
                    .bucket("my-pdfs")
                    .object("file1.pdf")
                    .contentType("application/pdf")
                    .stream(inputStream, UNKNOWN_OBJECT_SIZE, MIN_PART_SIZE)
                    .build();
            return minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public GetObjectResponse getObject(String name) {
        try {
            var getObjectArgs = GetObjectArgs.builder()
                    .bucket("my-pdfs")
                    .object(name)
                    .build();
            return minioClient.getObject(getObjectArgs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
