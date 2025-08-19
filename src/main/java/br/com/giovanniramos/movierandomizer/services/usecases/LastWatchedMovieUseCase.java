package br.com.giovanniramos.movierandomizer.services.usecases;

import br.com.giovanniramos.movierandomizer.exceptions.NotFoundException;
import br.com.giovanniramos.movierandomizer.models.MovieModel;
import br.com.giovanniramos.movierandomizer.repositories.LastMovieDrawRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LastWatchedMovieUseCase {
    private final LastMovieDrawRepository lastMovieDrawRepository;
    private final GetMovieDetailsUseCase getMovieDetailsUseCase;

    public MovieModel execute() {
        return lastMovieDrawRepository.findFirstBy()
                .map(lastMovieDrawEntity -> getMovieDetailsUseCase.execute(lastMovieDrawEntity.getLastDrawMovieId()))
                .orElseThrow(() -> new NotFoundException("No movies watched yet"));
    }
}
