package co.edu.uceva.serviciosGenerales.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import co.edu.uceva.serviciosGenerales.persistence.entity.UserEntity;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Devuelve todos los usuarios activos (active = true).
     */
    Page<UserEntity> findByActiveTrue(Pageable pageable);

    /**
     * Encuentra un usuario activo por ID.
     */
    Optional<UserEntity> findByIdAndActiveTrue(Long id);

    /**
     * Encuentra un usuario activo por correo electrónico institucional.
     */
    @Query(value = "SELECT * FROM user WHERE active = true AND institutional_email = :email", nativeQuery = true)
    Optional<UserEntity> findByEmail(String email);
}
