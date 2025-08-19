package br.com.giovanniramos.movierandomizer.services.usecases;

import br.com.giovanniramos.movierandomizer.exceptions.ConflictException;
import br.com.giovanniramos.movierandomizer.repositories.GenreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.giovanniramos.movierandomizer.mocks.GenreMock.genreEntityMock;
import static br.com.giovanniramos.movierandomizer.mocks.GenreMock.genreModelMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddGenreUseCaseTest {
    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private AddGenreUseCase addGenreUseCase;

    @Test
    void shouldAddGenreSuccessfully() {
        final var genreEntity = genreEntityMock();

        when(genreRepository.existsByName(any())).thenReturn(false);
        when(genreRepository.save(any())).thenReturn(genreEntity);

        final var response = assertDoesNotThrow(() -> addGenreUseCase.execute(genreModelMock()));

        assertEquals(genreEntity.getId(), response.getId());
        assertEquals(genreEntity.getName(), response.getName());
        assertEquals(genreEntity.getDescription(), response.getDescription());
    }

    @Test
    void shouldTryToAddGenreAndReturnConflictException() {
        final var genreModel = genreModelMock();

        when(genreRepository.existsByName(any())).thenReturn(true);

        final var conflictException = assertThrows(ConflictException.class, () -> addGenreUseCase.execute(genreModel));

        assertEquals(409, conflictException.getCode());
        assertEquals("Genre already exists", conflictException.getMessage());
    }
}