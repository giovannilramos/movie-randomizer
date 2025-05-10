package br.com.giovanniramos.movie_randomizer.repositories;

import br.com.giovanniramos.movie_randomizer.entities.MovieEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends MongoRepository<MovieEntity, String>, MovieCriteriaQueriesRepository {
    boolean existsByNameIgnoreCase(final String name);
}
