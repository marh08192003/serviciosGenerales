package co.edu.uceva.serviciosGenerales.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.uceva.serviciosGenerales.persistence.entity.UsuarioEntity;

import java.util.Optional;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    
    /**
     * Retorna todos los usuarios cuyo 'activo' sea true (usuarios "no eliminados").
     */
    List<UsuarioEntity> findByActivoTrue();

    /**
     * Retorna un usuario con el ID dado, siempre que 'activo' sea true.
     */
    Optional<UsuarioEntity> findByIdAndActivoTrue(Long id);
}
