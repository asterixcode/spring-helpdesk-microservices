package com.asterixcode.authserviceapi.repository;

import com.asterixcode.authserviceapi.models.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByEmail(final String email);
}
