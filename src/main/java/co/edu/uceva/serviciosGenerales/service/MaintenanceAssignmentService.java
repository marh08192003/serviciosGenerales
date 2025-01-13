package co.edu.uceva.serviciosGenerales.service;

import co.edu.uceva.serviciosGenerales.service.model.dto.MaintenanceAssignmentDTO;
import co.edu.uceva.serviciosGenerales.service.model.dto.MaintenanceDTO;

import java.util.List;

/**
 * Interface para el servicio de gestión de asignaciones de mantenimiento.
 * Define las operaciones principales relacionadas con las asignaciones de
 * mantenimiento.
 */
public interface MaintenanceAssignmentService {

    /**
     * Crea una nueva asignación de mantenimiento.
     * 
     * @param maintenanceAssignmentDTO Datos de la asignación a crear.
     * @return La asignación creada.
     */
    MaintenanceAssignmentDTO createMaintenanceAssignment(MaintenanceAssignmentDTO maintenanceAssignmentDTO);

    /**
     * Actualiza una asignación de mantenimiento existente.
     * 
     * @param maintenanceAssignmentDTO Datos actualizados de la asignación.
     * @return La asignación actualizada.
     */
    MaintenanceAssignmentDTO updateMaintenanceAssignment(MaintenanceAssignmentDTO maintenanceAssignmentDTO);

    /**
     * Obtiene una asignación de mantenimiento por su ID.
     * 
     * @param id ID de la asignación.
     * @return La asignación encontrada.
     */
    MaintenanceAssignmentDTO getMaintenanceAssignmentById(Long id);

    /**
     * Lista todas las asignaciones de mantenimiento activas.
     * 
     * @return Lista de asignaciones activas.
     */
    List<MaintenanceAssignmentDTO> listMaintenanceAssignments();

    /**
     * Realiza un soft delete de una asignación de mantenimiento cambiando su estado
     * a inactivo.
     * 
     * @param id ID de la asignación a eliminar.
     */
    void deleteMaintenanceAssignment(Long id);

    List<MaintenanceDTO> listMaintenancesAssignedToUser(Long userId);

    public List<MaintenanceAssignmentDTO> listAssignmentsForUser(Long userId);

}