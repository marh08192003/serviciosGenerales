package co.edu.uceva.serviciosGenerales.persistence.repository;

import co.edu.uceva.serviciosGenerales.persistence.entity.IncidentEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio para la gestión de operaciones con la entidad IncidentEntity.
 * Proporciona métodos para consultar incidencias activas y filtrar por usuario o área física.
 */
public interface IncidentRepository extends JpaRepository<IncidentEntity, Long> {

    /**
     * Encuentra todas las incidencias que están activas.
     * 
     * @return Lista de incidencias activas.
     */
    Page<IncidentEntity> findByActiveTrue(Pageable pageable);

    /**
     * Encuentra todas las incidencias activas reportadas por un usuario específico.
     * 
     * @param userId Identificador del usuario.
     * @return Lista de incidencias activas del usuario.
     */
    List<IncidentEntity> findByUserIdAndActiveTrue(Long userId);

    /**
     * Encuentra todas las incidencias activas asociadas a un área física específica.
     * 
     * @param physicalAreaId Identificador del área física.
     * @return Lista de incidencias activas del área física.
     */
    List<IncidentEntity> findByPhysicalAreaIdAndActiveTrue(Long physicalAreaId);
}
