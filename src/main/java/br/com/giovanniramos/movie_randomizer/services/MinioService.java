package br.com.giovanniramos.movie_randomizer.services;

import br.com.giovanniramos.movie_randomizer.exceptions.InternalServerErrorException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public void putMinioObject(final MultipartFile movieCover, final String object) {
        try {
            final var inputStream = movieCover.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(object)
                    .stream(inputStream, inputStream.available(), PART_SIZE)
                    .contentType(CONTENT_TYPE)
                    .build());
        } catch (final Exception e) {
            log.error("Minio put object failed. object: {}, error: {}", object, e.getMessage(), e);
            throw new InternalServerErrorException("Put movie cover failed", e);
        }
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

    public void removeMinioObject(final String object) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(object).build());
        } catch (final Exception e) {
            log.error("Error when remove file from minio. object: {}, error: {}", object, e.getCause(), e);
            throw new InternalServerErrorException("Error when remove movie cover", e);
        }
    }
}
