package br.com.giovanniramos.movie_randomizer.services;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
public class MinioService {
    private static final int PART_SIZE = -1;
    private static final String CONTENT_TYPE = "image/png";

    private final MinioClient minioClient;
    private final String bucket;

    public MinioService(final MinioClient minioClient, @Value("${minio.bucket}") final String bucket) {
        this.minioClient = minioClient;
        this.bucket = bucket;
    }

    public void putMinioObject(final InputStream fileInputStream, final String object) throws IOException, ServerException,
            InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidResponseException, XmlParserException, InternalException {
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .stream(fileInputStream, fileInputStream.available(), PART_SIZE)
                .contentType(CONTENT_TYPE)
                .build());
    }

    public String getMinioObjectUrl(final String object) {
        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucket)
                            .object(object)
                            .build()
            );
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucket)
                    .object(object)
                    .method(Method.GET)
                    .build());
        } catch (final Exception e) {
            log.error("Get file into Minio failed. error: {}", e.getMessage(), e);
            return null;
        }
    }

    public void removeMinioObject(final String object) throws ServerException, InsufficientDataException,
            ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
            XmlParserException, InternalException {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(object).build());
    }
}
