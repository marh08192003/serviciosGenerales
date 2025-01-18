package co.edu.uceva.serviciosGenerales.service;

import co.edu.uceva.serviciosGenerales.service.model.dto.IncidentDTO;

import java.util.List;

import org.springframework.data.domain.Page;

/**
 * Interface para el servicio de gestión de incidencias.
 * Define las operaciones principales relacionadas con las incidencias.
 */
public interface IncidentService {

    /**
     * Crea una nueva incidencia y la devuelve como un DTO.
     * 
     * @param incidentDTO Datos de la incidencia a crear.
     * @return La incidencia creada como un DTO.
     */
    IncidentDTO createIncident(IncidentDTO incidentDTO);

    /**
     * Actualiza una incidencia existente.
     * 
     * @param id          Identificador de la incidencia a actualizar.
     * @param incidentDTO Datos actualizados de la incidencia.
     * @return La incidencia actualizada como un DTO.
     */
    IncidentDTO updateIncident(Long id, IncidentDTO incidentDTO);

    /**
     * Obtiene una incidencia por su ID.
     * 
     * @param id Identificador de la incidencia.
     * @return La incidencia como un DTO si se encuentra.
     */
    IncidentDTO getIncidentById(Long id);

    /**
     * Lista todas las incidencias activas.
     * 
     * @return Lista de incidencias activas como DTOs.
     */
    Page<IncidentDTO> listActiveIncidents(int page, int size);
    /**
     * Elimina (soft delete) una incidencia cambiando su estado a inactivo.
     * 
     * @param id Identificador de la incidencia a eliminar.
     */
    void deleteIncident(Long id);

    /**
     * Lista todas las incidencias activas asociadas a un área física específica.
     * 
     * @param physicalAreaId Identificador del área física.
     * @return Lista de incidencias activas como DTOs.
     */
    List<IncidentDTO> listIncidentsByPhysicalArea(Long physicalAreaId);

    /**
     * Lista todas las incidencias activas asociadas a un usuario específico.
     *
     * @param userId Identificador del usuario.
     * @return Lista de incidencias activas como DTOs.
     */
    List<IncidentDTO> listIncidentsByUserId(Long userId);
}
