package br.com.giovanniramos.movierandomizer.repositories;

import br.com.giovanniramos.movierandomizer.entities.MovieEntity;
import br.com.giovanniramos.movierandomizer.models.MovieModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieCriteriaQueriesRepository {
    Page<MovieEntity> findAllByFilters(final MovieModel movieModel, final Pageable pageable);
}
