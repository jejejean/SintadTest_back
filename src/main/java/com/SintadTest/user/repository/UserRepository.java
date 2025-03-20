package com.SintadTest.user.repository;

import com.SintadTest.user.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserEntityByEmail(String email);
    boolean existsUserEntityByEmail(String email);

}
