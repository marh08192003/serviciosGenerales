package co.edu.uceva.serviciosGenerales.persistence.repository;

import co.edu.uceva.serviciosGenerales.persistence.entity.MantenimientoAsignacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MantenimientoAsignacionRepository extends JpaRepository<MantenimientoAsignacionEntity, Long> {

    List<MantenimientoAsignacionEntity> findByActivoTrue();

    Optional<MantenimientoAsignacionEntity> findByIdAndActivoTrue(Long id);
}