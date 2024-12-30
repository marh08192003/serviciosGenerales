package co.edu.uceva.serviciosGenerales.persistence.repository;

import co.edu.uceva.serviciosGenerales.persistence.entity.MantenimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface MantenimientoRepository extends JpaRepository<MantenimientoEntity, Long> {

    List<MantenimientoEntity> findByActivoTrue();

    Optional<MantenimientoEntity> findByIdAndActivoTrue(Long id);
}
