package br.com.giovanniramos.movierandomizer.repositories;

import br.com.giovanniramos.movierandomizer.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(final String username);
}
