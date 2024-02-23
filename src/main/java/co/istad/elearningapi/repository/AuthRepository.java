package co.istad.elearningapi.repository;

import co.istad.elearningapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long>{
@Modifying
  @Query(" UPDATE User AS u SET u.verifiedCode = ?2 WHERE u.email = ?1")
  void updateVerifiedCode(String email, String verifiedCode);
  Optional<User> findByEmailAndVerifiedCode(String email, String verifiedCode);
}
