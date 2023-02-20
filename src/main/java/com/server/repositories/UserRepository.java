package com.server.repositories;

import com.server.entities.User;
import com.server.repositories.projections.LoginResultProjection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    LoginResultProjection findByUsername(String username);
}
