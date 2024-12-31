package co.edu.uceva.serviciosGenerales.persistence.repository;

import co.edu.uceva.serviciosGenerales.persistence.entity.MaintenanceAssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de operaciones con la entidad
 * MaintenanceAssignmentEntity.
 * Proporciona métodos adicionales para consultar asignaciones activas.
 */
public interface MaintenanceAssignmentRepository extends JpaRepository<MaintenanceAssignmentEntity, Long> {

    /**
     * Encuentra todas las asignaciones que están activas.
     * 
     * @return Lista de asignaciones activas.
     */
    List<MaintenanceAssignmentEntity> findByActiveTrue();

    /**
     * Encuentra una asignación por su ID si está activa.
     * 
     * @param id Identificador de la asignación.
     * @return Asignación activa si se encuentra.
     */
    Optional<MaintenanceAssignmentEntity> findByIdAndActiveTrue(Long id);
}
