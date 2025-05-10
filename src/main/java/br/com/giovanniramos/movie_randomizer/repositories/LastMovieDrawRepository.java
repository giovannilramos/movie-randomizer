package br.com.giovanniramos.movie_randomizer.repositories;

import br.com.giovanniramos.movie_randomizer.entities.LastMovieDrawEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LastMovieDrawRepository extends MongoRepository<LastMovieDrawEntity, String> {
    Optional<LastMovieDrawEntity> findFirstBy();
}
