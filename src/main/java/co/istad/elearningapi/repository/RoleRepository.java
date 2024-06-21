package co.istad.elearningapi.repository;

import co.istad.elearningapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
Optional<Role> findByName( String name);
}
