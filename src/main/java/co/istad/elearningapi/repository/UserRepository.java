package co.istad.elearningapi.repository;

import co.istad.elearningapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Long> {

boolean existsByEmail(String email);
boolean existsByUsername(String username);
Optional<User> findByEmailAndIsDeletedAndIsVerified(String email, Boolean isDeleted, Boolean isVerified);
}
