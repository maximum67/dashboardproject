package com.example.dashboardproject.repositories;

import com.example.dashboardproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);

}
