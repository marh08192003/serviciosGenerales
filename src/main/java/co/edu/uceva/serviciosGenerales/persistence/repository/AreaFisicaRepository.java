package co.edu.uceva.serviciosGenerales.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uceva.serviciosGenerales.persistence.entity.AreaFisicaEntity;

public interface AreaFisicaRepository extends JpaRepository <AreaFisicaEntity, Long>{
    List<AreaFisicaEntity> findAllByActivoTrue();

     /**
     * Encuentra un área física por su ID y que esté activa.
     */
    Optional<AreaFisicaEntity> findByIdAndActivoTrue(Long id);
}
