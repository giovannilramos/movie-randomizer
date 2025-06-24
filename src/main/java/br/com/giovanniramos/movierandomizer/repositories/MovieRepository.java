package br.com.giovanniramos.movierandomizer.repositories;

import br.com.giovanniramos.movierandomizer.entities.MovieEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends MongoRepository<MovieEntity, String>, MovieCriteriaQueriesRepository {
    boolean existsByNameIgnoreCase(final String name);
}
