package co.edu.uceva.serviciosGenerales.service;

import co.edu.uceva.serviciosGenerales.service.model.dto.MantenimientoAsignacionDTO;

import java.util.List;

public interface MantenimientoAsignacionService {

    MantenimientoAsignacionDTO crearMantenimientoAsignacion(MantenimientoAsignacionDTO mantenimientoAsignacionDTO);

    MantenimientoAsignacionDTO actualizarMantenimientoAsignacion(MantenimientoAsignacionDTO mantenimientoAsignacionDTO);

    MantenimientoAsignacionDTO obtenerMantenimientoAsignacionPorId(Long id);

    List<MantenimientoAsignacionDTO> listarMantenimientosAsignaciones();

    void eliminarMantenimientoAsignacion(Long id);
}