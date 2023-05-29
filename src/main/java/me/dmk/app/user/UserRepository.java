package me.dmk.app.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by DMK on 17.04.2023
 */
@Repository
@Transactional(readOnly = true)
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{email: '?0'}")
    Optional<User> findUserByEmail(String email);
}
