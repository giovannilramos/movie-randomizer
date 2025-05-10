package br.com.giovanniramos.movie_randomizer.services;

import br.com.giovanniramos.movie_randomizer.models.MovieModel;
import br.com.giovanniramos.movie_randomizer.services.usecases.AddMovieUseCase;
import br.com.giovanniramos.movie_randomizer.services.usecases.GetMovieDetailsUseCase;
import br.com.giovanniramos.movie_randomizer.services.usecases.LastWatchedMovieUseCase;
import br.com.giovanniramos.movie_randomizer.services.usecases.MovieDrawUseCase;
import br.com.giovanniramos.movie_randomizer.services.usecases.RemoveMovieUseCase;
import br.com.giovanniramos.movie_randomizer.services.usecases.ShowMovieListUseCase;
import br.com.giovanniramos.movie_randomizer.services.usecases.UpdateMovieCoverUseCase;
import br.com.giovanniramos.movie_randomizer.services.usecases.UpdateMovieUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final AddMovieUseCase addMovieUseCase;
    private final ShowMovieListUseCase showMovieListUseCase;
    private final GetMovieDetailsUseCase getMovieDetailsUseCase;
    private final MovieDrawUseCase movieDrawUseCase;
    private final UpdateMovieUseCase updateMovieUseCase;
    private final LastWatchedMovieUseCase lastWatchedMovieUseCase;
    private final UpdateMovieCoverUseCase updateMovieCoverUseCase;
    private final RemoveMovieUseCase removeMovieUseCase;

    public MovieModel addMovie(final MovieModel movieModel) {
        return addMovieUseCase.execute(movieModel);
    }

    public Page<MovieModel> showMovieList(final MovieModel movieModel, final Pageable pageable) {
        return showMovieListUseCase.execute(movieModel, pageable);
    }

    public MovieModel getMovieDetails(final String id) {
        return getMovieDetailsUseCase.execute(id);
    }

    public MovieModel movieDraw(final Set<String> moviesId) {
        return movieDrawUseCase.execute(moviesId);
    }

    public MovieModel updateMovie(final MovieModel movieModel) {
        return updateMovieUseCase.execute(movieModel);
    }

    public void updateMovieCover(final String id, final MultipartFile movieCover) {
        updateMovieCoverUseCase.execute(id, movieCover);
    }

    public MovieModel lastWatchedMovie() {
        return lastWatchedMovieUseCase.execute();
    }

    public void removeMovie(final String id) {
        removeMovieUseCase.execute(id);
    }
}
