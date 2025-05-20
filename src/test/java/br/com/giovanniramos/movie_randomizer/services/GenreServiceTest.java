package br.com.giovanniramos.movie_randomizer.services;

import br.com.giovanniramos.movie_randomizer.services.usecases.AddGenreUseCase;
import br.com.giovanniramos.movie_randomizer.services.usecases.ListGenresUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static br.com.giovanniramos.movie_randomizer.services.mocks.GenreMock.genreModelMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenreServiceTest {

    @Mock
    private AddGenreUseCase addGenreUseCase;

    @Mock
    private ListGenresUseCase listGenresUseCase;

    @InjectMocks
    private GenreService genreService;

    @Test
    void shouldAddGenre() {
        final var genreModel = genreModelMock();

        when(addGenreUseCase.execute(any())).thenReturn(genreModel);

        assertDoesNotThrow(() -> genreService.addGenre(genreModel));

        verify(addGenreUseCase, times(1)).execute(any());
    }

    @Test
    void shouldListGenres() {
        when(listGenresUseCase.execute()).thenReturn(List.of(genreModelMock()));

        assertDoesNotThrow(() -> genreService.listGenres());

        verify(listGenresUseCase, times(1)).execute();
    }
}