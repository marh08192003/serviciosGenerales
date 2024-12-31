package co.edu.uceva.serviciosGenerales.service.impl;

import co.edu.uceva.serviciosGenerales.exception.ResourceNotFoundException;
import co.edu.uceva.serviciosGenerales.persistence.entity.PhysicalAreaEntity;
import co.edu.uceva.serviciosGenerales.persistence.entity.IncidentStatus;
import co.edu.uceva.serviciosGenerales.persistence.entity.IncidentEntity;
import co.edu.uceva.serviciosGenerales.persistence.entity.UserEntity;
import co.edu.uceva.serviciosGenerales.persistence.repository.PhysicalAreaRepository;
import co.edu.uceva.serviciosGenerales.persistence.repository.IncidentRepository;
import co.edu.uceva.serviciosGenerales.persistence.repository.UserRepository;
import co.edu.uceva.serviciosGenerales.service.IncidentService;
import co.edu.uceva.serviciosGenerales.service.model.dto.IncidentDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio de gestión de incidencias.
 * Proporciona métodos para crear, actualizar, obtener, listar y eliminar
 * incidencias.
 */
@Service
@Transactional
public class IncidentServiceImpl implements IncidentService {

    private static final String INCIDENT_NOT_FOUND = "Incidencia no encontrada";
    private static final String USER_NOT_FOUND = "Usuario no encontrado o inactivo";
    private static final String PHYSICAL_AREA_NOT_FOUND = "Área física no encontrada o inactiva";

    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;
    private final PhysicalAreaRepository physicalAreaRepository;

    public IncidentServiceImpl(IncidentRepository incidentRepository,
            UserRepository userRepository,
            PhysicalAreaRepository physicalAreaRepository) {
        this.incidentRepository = incidentRepository;
        this.userRepository = userRepository;
        this.physicalAreaRepository = physicalAreaRepository;
    }

    @Override
    public IncidentDTO createIncident(IncidentDTO incidentDTO) {
        UserEntity user = userRepository.findByIdAndActiveTrue(incidentDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        PhysicalAreaEntity physicalArea = physicalAreaRepository.findByIdAndActiveTrue(incidentDTO.getPhysicalAreaId())
                .orElseThrow(() -> new ResourceNotFoundException(PHYSICAL_AREA_NOT_FOUND));

        IncidentEntity incident = new IncidentEntity();
        incident.setUser(user);
        incident.setPhysicalArea(physicalArea);
        incident.setDescription(incidentDTO.getDescription());
        incident.setStatus(incidentDTO.getStatus() != null ? incidentDTO.getStatus() : IncidentStatus.pendiente);
        incident.setActive(true);

        IncidentEntity savedIncident = incidentRepository.save(incident);
        return mapToDTO(savedIncident);
    }

    @Override
    public IncidentDTO updateIncident(Long id, IncidentDTO incidentDTO) {
        IncidentEntity incident = incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(INCIDENT_NOT_FOUND));

        if (incidentDTO.getPhysicalAreaId() != null) {
            PhysicalAreaEntity physicalArea = physicalAreaRepository
                    .findByIdAndActiveTrue(incidentDTO.getPhysicalAreaId())
                    .orElseThrow(() -> new ResourceNotFoundException(PHYSICAL_AREA_NOT_FOUND));
            incident.setPhysicalArea(physicalArea);
        }

        incident.setDescription(incidentDTO.getDescription());
        incident.setStatus(incidentDTO.getStatus() != null ? incidentDTO.getStatus() : incident.getStatus());
        incident.setActive(incidentDTO.getActive());

        IncidentEntity updatedIncident = incidentRepository.save(incident);
        return mapToDTO(updatedIncident);
    }

    @Override
    public IncidentDTO getIncidentById(Long id) {
        IncidentEntity incident = incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(INCIDENT_NOT_FOUND));
        return mapToDTO(incident);
    }

    @Override
    public List<IncidentDTO> listActiveIncidents() {
        return incidentRepository.findByActiveTrue().stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void deleteIncident(Long id) {
        IncidentEntity incident = incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(INCIDENT_NOT_FOUND));

        incident.setActive(false);
        incidentRepository.save(incident);
    }

    @Override
    public List<IncidentDTO> listIncidentsByPhysicalArea(Long physicalAreaId) {
        List<IncidentEntity> incidents = incidentRepository.findByPhysicalAreaIdAndActiveTrue(physicalAreaId);

        if (incidents.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No se encontraron incidencias para el área física con ID: " + physicalAreaId);
        }

        return incidents.stream()
                .map(this::mapToDTO)
                .toList();
    }

    // -------------------------------------------------------------------------------------------
    // Métodos privados de mapeo
    // -------------------------------------------------------------------------------------------

    private IncidentDTO mapToDTO(IncidentEntity entity) {
        IncidentDTO dto = new IncidentDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setPhysicalAreaId(entity.getPhysicalArea().getId());
        dto.setDescription(entity.getDescription());
        dto.setReportDate(entity.getReportDate());
        dto.setStatus(entity.getStatus());
        dto.setActive(entity.getActive());
        return dto;
    }
}
