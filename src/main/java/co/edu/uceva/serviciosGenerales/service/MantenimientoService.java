package co.edu.uceva.serviciosGenerales.service;

import co.edu.uceva.serviciosGenerales.service.model.dto.MantenimientoDTO;

import java.util.List;

public interface MantenimientoService {

    /**
     * Crea un nuevo mantenimiento.
     * 
     * @param mantenimientoDTO DTO con los datos del mantenimiento.
     * @return El mantenimiento creado.
     */
    MantenimientoDTO crearMantenimiento(MantenimientoDTO mantenimientoDTO);

    /**
     * Actualiza un mantenimiento existente.
     * 
     * @param mantenimientoDTO DTO con los datos actualizados del mantenimiento.
     * @return El mantenimiento actualizado.
     */
    MantenimientoDTO actualizarMantenimiento(MantenimientoDTO mantenimientoDTO);

    /**
     * Obtiene un mantenimiento por su ID.
     * 
     * @param id ID del mantenimiento.
     * @return DTO del mantenimiento encontrado.
     */
    MantenimientoDTO obtenerMantenimientoPorId(Long id);

    /**
     * Lista todos los mantenimientos activos.
     * 
     * @return Lista de mantenimientos activos.
     */
    List<MantenimientoDTO> listarMantenimientos();

    /**
     * Realiza un soft delete de un mantenimiento cambiando su estado a inactivo.
     * 
     * @param id ID del mantenimiento a eliminar.
     */
    void eliminarMantenimiento(Long id);
}
