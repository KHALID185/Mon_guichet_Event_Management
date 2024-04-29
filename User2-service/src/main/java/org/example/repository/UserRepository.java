package org.example.repository;

import org.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.username = :#{#user.username} OR u.email = :#{#user.email}")
    boolean existsUser(
            @Param("user")
            User user
    );
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(
            String username,
            String password
    );
}