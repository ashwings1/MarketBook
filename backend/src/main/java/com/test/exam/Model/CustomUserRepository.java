package com.test.exam.Model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
    Optional<CustomUser> findByUsername(String username);
    Optional<CustomUser> findByFirstName(String firstName);
    Optional<CustomUser> findByLastName(String lastName);
    Optional<CustomUser> findByRole(Role role);
}
