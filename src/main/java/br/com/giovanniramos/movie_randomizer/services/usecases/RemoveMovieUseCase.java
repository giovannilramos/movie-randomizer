package br.com.giovanniramos.movie_randomizer.services.usecases;

import br.com.giovanniramos.movie_randomizer.entities.MovieEntity;
import br.com.giovanniramos.movie_randomizer.exceptions.InternalServerErrorException;
import br.com.giovanniramos.movie_randomizer.exceptions.NotFoundException;
import br.com.giovanniramos.movie_randomizer.repositories.MovieRepository;
import br.com.giovanniramos.movie_randomizer.services.MinioService;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoveMovieUseCase {
    private final MovieRepository movieRepository;
    private final MinioService minioService;

    public void execute(final String id) {
        final var movieEntity = movieRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Movie not found when delete movie. id: {}", id);
                    return new NotFoundException("Movie not found");
                });

        try {
            removeMovie(movieEntity);
        } catch (final Exception e) {
            log.error("Error when remove file from minio. id: {}, movieCoverId: {}, error: {}",
                    id, movieEntity.getMovieCoverId(), e.getCause(), e);
            throw new InternalServerErrorException("Error when remove movie cover", e);
        }
    }

    private void removeMovie(final MovieEntity movieEntity) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioService.removeMinioObject(movieEntity.getMovieCoverId());
        movieRepository.delete(movieEntity);
    }
}
