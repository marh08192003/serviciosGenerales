package co.edu.uceva.serviciosGenerales.persistence.repository;

import co.edu.uceva.serviciosGenerales.persistence.entity.MaintenanceEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para la gestión de operaciones con la entidad MaintenanceEntity.
 * Proporciona métodos adicionales para consultar mantenimientos activos.
 */
public interface MaintenanceRepository extends JpaRepository<MaintenanceEntity, Long> {

    /**
     * Encuentra todos los mantenimientos que están activos.
     * 
     * @return Lista de mantenimientos activos.
     */
    Page<MaintenanceEntity> findByActiveTrue(Pageable pageable);

    /**
     * Encuentra un mantenimiento por su ID si está activo.
     * 
     * @param id Identificador del mantenimiento.
     * @return Mantenimiento activo si se encuentra.
     */
    Optional<MaintenanceEntity> findByIdAndActiveTrue(Long id);
}
