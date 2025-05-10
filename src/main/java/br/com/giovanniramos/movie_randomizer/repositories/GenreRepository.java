package br.com.giovanniramos.movie_randomizer.repositories;

import br.com.giovanniramos.movie_randomizer.entities.GenreEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends MongoRepository<GenreEntity, String> {
    boolean existsByName(final String name);
}
