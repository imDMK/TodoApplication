package me.dmk.app.user.repository;

import me.dmk.app.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by DMK on 17.04.2023
 */

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{email: '?0'}")
    User findUserByEmail(String email);
}
