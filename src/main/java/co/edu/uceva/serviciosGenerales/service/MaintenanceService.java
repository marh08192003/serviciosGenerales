package co.edu.uceva.serviciosGenerales.service;

import co.edu.uceva.serviciosGenerales.service.model.dto.MaintenanceDTO;

import java.util.List;

/**
 * Interface para el servicio de gestión de mantenimientos.
 * Define las operaciones principales relacionadas con los mantenimientos.
 */
public interface MaintenanceService {

    /**
     * Crea un nuevo mantenimiento.
     * 
     * @param maintenanceDTO DTO con los datos del mantenimiento.
     * @return El mantenimiento creado.
     */
    MaintenanceDTO createMaintenance(MaintenanceDTO maintenanceDTO);

    /**
     * Actualiza un mantenimiento existente.
     * 
     * @param maintenanceDTO DTO con los datos actualizados del mantenimiento.
     * @return El mantenimiento actualizado.
     */
    MaintenanceDTO updateMaintenance(MaintenanceDTO maintenanceDTO);

    /**
     * Obtiene un mantenimiento por su ID.
     * 
     * @param id ID del mantenimiento.
     * @return DTO del mantenimiento encontrado.
     */
    MaintenanceDTO getMaintenanceById(Long id);

    /**
     * Lista todos los mantenimientos activos.
     * 
     * @return Lista de mantenimientos activos.
     */
    List<MaintenanceDTO> listMaintenances();

    /**
     * Realiza un soft delete de un mantenimiento cambiando su estado a inactivo.
     * 
     * @param id ID del mantenimiento a eliminar.
     */
    void deleteMaintenance(Long id);
} 
