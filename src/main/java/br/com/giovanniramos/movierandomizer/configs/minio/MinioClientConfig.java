package br.com.giovanniramos.movierandomizer.configs.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioClientConfig {
    private final String accessKey;
    private final String secretKey;
    private final String endpoint;

    public MinioClientConfig(@Value("${minio.accessKey}") final String accessKey,
                             @Value("${minio.secretKey}") final String secretKey,
                             @Value("${minio.endpoint}") final String endpoint) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.endpoint = endpoint;
    }

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
