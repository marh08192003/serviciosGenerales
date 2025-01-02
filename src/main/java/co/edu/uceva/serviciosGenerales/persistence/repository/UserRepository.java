package co.edu.uceva.serviciosGenerales.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uceva.serviciosGenerales.persistence.entity.UserEntity;

import java.util.Optional;
import java.util.List;

/**
 * Repository interface for UserEntity. Provides custom methods for retrieving
 * non-deleted users. 
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Returns all users where 'active' is true (non-deleted users).
     */
    List<UserEntity> findByActiveTrue();

    /**
     * Returns a user with the given ID, as long as 'active' is true.
     */
    Optional<UserEntity> findByIdAndActiveTrue(Long id);
}