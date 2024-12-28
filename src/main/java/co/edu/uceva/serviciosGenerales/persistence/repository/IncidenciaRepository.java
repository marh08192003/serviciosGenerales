package co.edu.uceva.serviciosGenerales.persistence.repository;

import co.edu.uceva.serviciosGenerales.persistence.entity.IncidenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidenciaRepository extends JpaRepository<IncidenciaEntity, Long> {

    List<IncidenciaEntity> findByActivoTrue();

    List<IncidenciaEntity> findByUsuarioIdAndActivoTrue(Long usuarioId);
}
