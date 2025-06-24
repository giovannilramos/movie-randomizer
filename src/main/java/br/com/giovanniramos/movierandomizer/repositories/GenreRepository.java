package br.com.giovanniramos.movierandomizer.repositories;

import br.com.giovanniramos.movierandomizer.entities.GenreEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends MongoRepository<GenreEntity, String> {
    boolean existsByName(final String name);
}
