package co.edu.uceva.serviciosGenerales.service;

import co.edu.uceva.serviciosGenerales.service.model.dto.AreaFisicaDTO;
import java.util.List;

public interface AreaFisicaService {

    /**
     * Crea una nueva área física y la devuelve en forma de DTO.
     */
    AreaFisicaDTO crearAreaFisica(AreaFisicaDTO areaFisicaDTO);

    /**
     * Actualiza una área física ya existente (usando su ID en el DTO).
     */
    AreaFisicaDTO actualizarAreaFisica(AreaFisicaDTO areaFisicaDTO);

    /**
     * Obtiene una área física por su ID.
     */
    AreaFisicaDTO obtenerAreaFisicaPorId(Long id);

    /**
     * Lista todas las áreas físicas activas.
     */
    List<AreaFisicaDTO> listarAreasFisicasActivas();

    /**
     * Desactiva (soft delete) una área física, cambiando su atributo 'activo' a false.
     */
    void eliminarAreaFisica(Long id);
}
