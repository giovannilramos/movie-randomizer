package br.com.giovanniramos.movierandomizer.services.usecases;

import br.com.giovanniramos.movierandomizer.exceptions.NotFoundException;
import br.com.giovanniramos.movierandomizer.repositories.MovieRepository;
import br.com.giovanniramos.movierandomizer.services.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateMovieCoverUseCase {
    private final MovieRepository movieRepository;
    private final MinioService minioService;

    public void execute(final String id, final MultipartFile movieCover) {
        final var movieEntity = movieRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Movie not found when update movie cover. id: {}", id);
                    return new NotFoundException("Movie not found");
                });

        minioService.putMinioObject(movieCover, movieEntity.getMovieCoverId());
    }
}
