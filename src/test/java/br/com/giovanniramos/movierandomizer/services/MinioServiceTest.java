package br.com.giovanniramos.movierandomizer.services;

import br.com.giovanniramos.movierandomizer.exceptions.InternalServerErrorException;
import io.minio.MinioClient;
import io.minio.errors.InvalidResponseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.security.InvalidKeyException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MinioServiceTest {

    private static final String OBJECT = UUID.randomUUID().toString();

    @Mock
    private MultipartFile multipartFile;

    @Mock
    private MinioClient minioClient;

    private MinioService minioService;

    @BeforeEach
    void setUp() {
        minioService = new MinioService(minioClient, "minio-bucket");
    }

    @Test
    void shouldPutMinioObject() throws Exception {
        when(multipartFile.getInputStream()).thenReturn(InputStream.nullInputStream());

        assertDoesNotThrow(() -> minioService.putMinioObject(multipartFile, OBJECT));

        verify(minioClient, times(1)).putObject(any());
    }

    @Test
    void shouldThrowExceptionWhenPutMinioObject() throws Exception {
        final var invalidResponseException = new InvalidResponseException(500, "Error", "Error", "Error");

        when(multipartFile.getInputStream()).thenReturn(InputStream.nullInputStream());
        doThrow(invalidResponseException).when(minioClient).putObject(any());

        final var internalServerErrorException = assertThrows(InternalServerErrorException.class,
                () -> minioService.putMinioObject(multipartFile, OBJECT));

        assertEquals("Put movie cover failed", internalServerErrorException.getMessage());
        assertEquals(invalidResponseException.getMessage(), internalServerErrorException.getCause().getMessage());
    }

    @Test
    void shouldGetMinioObjectUrl() throws Exception {
        final var url = "https://minio-url.com";
        when(minioClient.getPresignedObjectUrl(any())).thenReturn(url);

        final var presignedUrl = assertDoesNotThrow(() -> minioService.getMinioObjectUrl(OBJECT));

        assertEquals(url, presignedUrl);

        verify(minioClient, times(1)).statObject(any());
    }

    @Test
    void shouldReturnNullWhenFileNotExistOnMinio() throws Exception {
        doThrow(new InvalidKeyException()).when(minioClient).statObject(any());

        assertNull(minioService.getMinioObjectUrl(OBJECT));
    }

    @Test
    void shouldReturnNullWhenThrowExceptionWhileRecoveringMinioPresignedUrl() throws Exception {
        when(minioClient.getPresignedObjectUrl(any())).thenThrow(new InvalidKeyException());

        assertNull(minioService.getMinioObjectUrl(OBJECT));

        verify(minioClient, times(1)).statObject(any());
    }

    @Test
    void shouldRemoveMinioObject() throws Exception {
        assertDoesNotThrow(() -> minioService.removeMinioObject(OBJECT));

        verify(minioClient, times(1)).removeObject(any());
    }

    @Test
    void shouldThrowExceptionWhenRemoveMinioObject() throws Exception {
        final var invalidKeyException = new InvalidKeyException();

        doThrow(invalidKeyException).when(minioClient).removeObject(any());

        final var internalServerErrorException = assertThrows(InternalServerErrorException.class,
                () -> minioService.removeMinioObject(OBJECT));

        assertEquals("Error when remove movie cover", internalServerErrorException.getMessage());
        assertEquals(invalidKeyException.getMessage(), internalServerErrorException.getCause().getMessage());
    }
}