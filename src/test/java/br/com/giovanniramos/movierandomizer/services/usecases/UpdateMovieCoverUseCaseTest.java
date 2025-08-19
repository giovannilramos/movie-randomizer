package br.com.giovanniramos.movierandomizer.services.usecases;

import br.com.giovanniramos.movierandomizer.exceptions.NotFoundException;
import br.com.giovanniramos.movierandomizer.repositories.MovieRepository;
import br.com.giovanniramos.movierandomizer.services.MinioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;
import java.util.UUID;

import static br.com.giovanniramos.movierandomizer.mocks.MovieMock.movieEntityMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateMovieCoverUseCaseTest {
    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MinioService minioService;

    @InjectMocks
    private UpdateMovieCoverUseCase updateMovieCoverUseCase;

    @Test
    void shouldUpdateMovieCoverSuccessfully() {
        when(movieRepository.findById(anyString())).thenReturn(Optional.of(movieEntityMock()));

        assertDoesNotThrow(() -> updateMovieCoverUseCase.execute(UUID.randomUUID().toString(), validFile()));

        verify(minioService, times(1)).putMinioObject(any(), anyString());
    }

    @Test
    void shouldTryToUpdateMovieCoverAndReturnNotFoundException() {
        final var id = UUID.randomUUID().toString();
        final var movieCover = validFile();

        when(movieRepository.findById(anyString())).thenReturn(Optional.empty());

        final var notFoundException = assertThrows(NotFoundException.class, () -> updateMovieCoverUseCase.execute(id, movieCover));

        assertEquals(404, notFoundException.getCode());
        assertEquals("Movie not found", notFoundException.getMessage());

        verify(minioService, never()).putMinioObject(any(), anyString());
    }

    private MockMultipartFile validFile() {
        return new MockMultipartFile("movieCover", "cover.jpg", MediaType.IMAGE_JPEG_VALUE,
                "file".getBytes());
    }
}