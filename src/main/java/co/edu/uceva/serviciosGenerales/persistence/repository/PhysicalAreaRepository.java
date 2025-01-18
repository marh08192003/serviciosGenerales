package co.edu.uceva.serviciosGenerales.persistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uceva.serviciosGenerales.persistence.entity.PhysicalAreaEntity;

/**
 * Repositorio para la gestión de operaciones con la entidad PhysicalAreaEntity.
 * Proporciona métodos adicionales para manejar áreas físicas activas.
 */
public interface PhysicalAreaRepository extends JpaRepository<PhysicalAreaEntity, Long> {

    /**
     * Encuentra todas las áreas físicas que están marcadas como activas.
     * 
     * @return Lista de entidades PhysicalAreaEntity activas.
     */
    Page<PhysicalAreaEntity> findAllByActiveTrue(Pageable pageable);

    /**
     * Encuentra un área física por su ID y que esté activa.
     * 
     * @param id Identificador del área física.
     * @return Un Optional conteniendo el área física activa si se encuentra.
     */
    Optional<PhysicalAreaEntity> findByIdAndActiveTrue(Long id);
}
