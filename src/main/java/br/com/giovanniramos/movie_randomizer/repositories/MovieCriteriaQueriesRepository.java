package br.com.giovanniramos.movie_randomizer.repositories;

import br.com.giovanniramos.movie_randomizer.entities.MovieEntity;
import br.com.giovanniramos.movie_randomizer.models.MovieModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieCriteriaQueriesRepository {
    Page<MovieEntity> findAllByFilters(final MovieModel movieModel, final Pageable pageable);
}
