package co.edu.uceva.serviciosGenerales.persistence.repository;

import co.edu.uceva.serviciosGenerales.persistence.entity.MaintenanceAssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de operaciones con la entidad
 * MaintenanceAssignmentEntity.
 * Proporciona métodos adicionales para consultar asignaciones activas y
 * relacionadas.
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

    /**
     * Encuentra todas las asignaciones relacionadas con un mantenimiento
     * específico.
     * 
     * @param maintenanceId ID del mantenimiento asociado.
     * @return Lista de asignaciones relacionadas.
     */
    @Query("SELECT ma FROM MaintenanceAssignmentEntity ma WHERE ma.maintenance.id = :maintenanceId AND ma.active = true")
    List<MaintenanceAssignmentEntity> findByMaintenanceId(@Param("maintenanceId") Long maintenanceId);

    List<MaintenanceAssignmentEntity> findByUserIdAndActiveTrue(Long userId);

    @Query("SELECT CASE WHEN COUNT(ma) > 0 THEN true ELSE false END " +
            "FROM MaintenanceAssignmentEntity ma " +
            "WHERE ma.maintenance.id = :maintenanceId AND ma.user.id = :userId AND ma.active = true")
    boolean existsByMaintenanceIdAndUserIdAndActiveTrue(@Param("maintenanceId") Long maintenanceId,
            @Param("userId") Long userId);

}
