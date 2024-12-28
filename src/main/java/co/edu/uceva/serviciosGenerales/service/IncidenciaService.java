package co.edu.uceva.serviciosGenerales.service;

import co.edu.uceva.serviciosGenerales.service.model.dto.IncidenciaDTO;

import java.util.List;

public interface IncidenciaService {

    IncidenciaDTO crearIncidencia(IncidenciaDTO incidenciaDTO);

    IncidenciaDTO actualizarIncidencia(Long id, IncidenciaDTO incidenciaDTO);

    IncidenciaDTO obtenerIncidenciaPorId(Long id);

    List<IncidenciaDTO> listarIncidenciasActivas();

    void eliminarIncidencia(Long id);

    // Nuevo método
    List<IncidenciaDTO> listarIncidenciasPorAreaFisica(Long areaFisicaId);
}
