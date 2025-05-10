package br.com.giovanniramos.movie_randomizer.repositories;

import br.com.giovanniramos.movie_randomizer.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(final String username);
}
