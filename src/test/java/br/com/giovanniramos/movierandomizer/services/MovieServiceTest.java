package br.com.giovanniramos.movierandomizer.services;

import br.com.giovanniramos.movierandomizer.services.usecases.AddMovieUseCase;
import br.com.giovanniramos.movierandomizer.services.usecases.GetMovieDetailsUseCase;
import br.com.giovanniramos.movierandomizer.services.usecases.LastWatchedMovieUseCase;
import br.com.giovanniramos.movierandomizer.services.usecases.MovieDrawUseCase;
import br.com.giovanniramos.movierandomizer.services.usecases.RemoveMovieUseCase;
import br.com.giovanniramos.movierandomizer.services.usecases.ShowMovieListUseCase;
import br.com.giovanniramos.movierandomizer.services.usecases.UpdateMovieCoverUseCase;
import br.com.giovanniramos.movierandomizer.services.usecases.UpdateMovieUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static br.com.giovanniramos.movierandomizer.mocks.MovieMock.movieModelMock;
import static br.com.giovanniramos.movierandomizer.mocks.MovieMock.movieModelWithMovieCoverMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private AddMovieUseCase addMovieUseCase;

    @Mock
    private ShowMovieListUseCase showMovieListUseCase;

    @Mock
    private GetMovieDetailsUseCase getMovieDetailsUseCase;

    @Mock
    private MovieDrawUseCase movieDrawUseCase;

    @Mock
    private UpdateMovieUseCase updateMovieUseCase;

    @Mock
    private LastWatchedMovieUseCase lastWatchedMovieUseCase;

    @Mock
    private UpdateMovieCoverUseCase updateMovieCoverUseCase;

    @Mock
    private RemoveMovieUseCase removeMovieUseCase;

    @Mock
    private MultipartFile multiPartFile;

    @InjectMocks
    private MovieService movieService;

    @Test
    void shouldAddMovie() {
        final var movieModel = movieModelWithMovieCoverMock(multiPartFile);

        when(addMovieUseCase.execute(any())).thenReturn(movieModel);

        assertDoesNotThrow(() -> movieService.addMovie(movieModel));
    }

    @Test
    void shouldShowMovieList() {
        final var movieModel = movieModelMock();

        when(showMovieListUseCase.execute(any(), any())).thenReturn(new PageImpl<>(List.of(movieModel)));

        assertDoesNotThrow(() -> movieService.showMovieList(movieModel, PageRequest.of(0, 10)));
    }

    @Test
    void shouldGetMovieDetails() {
        when(getMovieDetailsUseCase.execute(any())).thenReturn(movieModelMock());

        assertDoesNotThrow(() -> movieService.getMovieDetails(UUID.randomUUID().toString()));
    }

    @Test
    void shouldDrawMovie() {
        final var movieModel = movieModelMock();

        when(movieDrawUseCase.execute(any())).thenReturn(movieModel);

        assertDoesNotThrow(() -> movieService.movieDraw(Set.of(movieModel.getId(), UUID.randomUUID().toString())));
    }

    @Test
    void shouldUpdateMovie() {
        final var movieModelMock = movieModelMock();

        when(updateMovieUseCase.execute(any())).thenReturn(movieModelMock);

        assertDoesNotThrow(() -> movieService.updateMovie(movieModelMock));
    }

    @Test
    void shouldUpdateMovieCover() {
        assertDoesNotThrow(() -> movieService.updateMovieCover(UUID.randomUUID().toString(), multiPartFile));

        verify(updateMovieCoverUseCase, times(1)).execute(any(), any());
    }

    @Test
    void shouldReturnLastWatchedMovie() {
        when(lastWatchedMovieUseCase.execute()).thenReturn(movieModelMock());

        assertDoesNotThrow(() -> movieService.lastWatchedMovie());
    }

    @Test
    void shouldRemoveMovie() {
        assertDoesNotThrow(() -> movieService.removeMovie(UUID.randomUUID().toString()));

        verify(removeMovieUseCase, times(1)).execute(any());
    }
}