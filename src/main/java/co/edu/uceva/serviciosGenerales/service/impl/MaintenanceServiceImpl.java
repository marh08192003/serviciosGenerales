package co.edu.uceva.serviciosGenerales.service.impl;

import co.edu.uceva.serviciosGenerales.exception.ResourceNotFoundException;
import co.edu.uceva.serviciosGenerales.persistence.entity.MaintenanceAssignmentEntity;
import co.edu.uceva.serviciosGenerales.persistence.entity.MaintenanceEntity;
import co.edu.uceva.serviciosGenerales.persistence.entity.PhysicalAreaEntity;
import co.edu.uceva.serviciosGenerales.persistence.repository.MaintenanceAssignmentRepository;
import co.edu.uceva.serviciosGenerales.persistence.repository.MaintenanceRepository;
import co.edu.uceva.serviciosGenerales.persistence.repository.PhysicalAreaRepository;
import co.edu.uceva.serviciosGenerales.service.MaintenanceService;
import co.edu.uceva.serviciosGenerales.service.model.dto.MaintenanceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;



import java.util.List;

/**
 * Implementación del servicio de gestión de mantenimientos.
 */
@Service
@Transactional
public class MaintenanceServiceImpl implements MaintenanceService {

    private static final String MAINTENANCE_NOT_FOUND_MESSAGE = "Mantenimiento no encontrado";
    private static final String PHYSICAL_AREA_NOT_FOUND_MESSAGE = "El área física no está activa o no existe";

    private final MaintenanceRepository maintenanceRepository;
    private final MaintenanceAssignmentRepository maintenanceAssignmentRepository;
    private final PhysicalAreaRepository physicalAreaRepository;

    public MaintenanceServiceImpl(MaintenanceRepository maintenanceRepository,
            MaintenanceAssignmentRepository maintenanceAssignmentRepository,
            PhysicalAreaRepository physicalAreaRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.maintenanceAssignmentRepository = maintenanceAssignmentRepository;
        this.physicalAreaRepository = physicalAreaRepository;
    }

    @Override
    public MaintenanceDTO createMaintenance(MaintenanceDTO maintenanceDTO) {
        PhysicalAreaEntity physicalArea = physicalAreaRepository
                .findByIdAndActiveTrue(maintenanceDTO.getPhysicalAreaId())
                .orElseThrow(() -> new ResourceNotFoundException(PHYSICAL_AREA_NOT_FOUND_MESSAGE));

        MaintenanceEntity entity = mapToEntity(maintenanceDTO);
        entity.setPhysicalArea(physicalArea);

        MaintenanceEntity savedEntity = maintenanceRepository.save(entity);
        return mapToDTO(savedEntity);
    }

    @Override
    public MaintenanceDTO updateMaintenance(MaintenanceDTO maintenanceDTO) {
        MaintenanceEntity existingEntity = maintenanceRepository.findByIdAndActiveTrue(maintenanceDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException(MAINTENANCE_NOT_FOUND_MESSAGE));

        existingEntity.setMaintenanceType(maintenanceDTO.getMaintenanceType());
        existingEntity.setStartDate(maintenanceDTO.getStartDate());
        existingEntity.setDuration(maintenanceDTO.getDuration());
        existingEntity.setDescription(maintenanceDTO.getDescription());
        existingEntity.setPriority(maintenanceDTO.getPriority());

        MaintenanceEntity updatedEntity = maintenanceRepository.save(existingEntity);
        return mapToDTO(updatedEntity);
    }

    @Override
    public MaintenanceDTO getMaintenanceById(Long id) {
        MaintenanceEntity entity = maintenanceRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(MAINTENANCE_NOT_FOUND_MESSAGE));
        return mapToDTO(entity);
    }

    @Override
    public Page<MaintenanceDTO> listMaintenances(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MaintenanceEntity> maintenancePage = maintenanceRepository.findByActiveTrue(pageable);

        // Convertir las entidades a DTOs
        return maintenancePage.map(this::mapToDTO);
    }

    @Override
    public void deleteMaintenance(Long id) {
        MaintenanceEntity maintenanceEntity = maintenanceRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(MAINTENANCE_NOT_FOUND_MESSAGE));

        // Eliminar (soft delete) todas las asignaciones relacionadas con el
        // mantenimiento
        List<MaintenanceAssignmentEntity> assignments = maintenanceAssignmentRepository.findByMaintenanceId(id);
        assignments.forEach(assignment -> assignment.setActive(false));
        maintenanceAssignmentRepository.saveAll(assignments);

        // Realizar soft delete del mantenimiento
        maintenanceEntity.setActive(false);
        maintenanceRepository.save(maintenanceEntity);
    }

    private MaintenanceEntity mapToEntity(MaintenanceDTO dto) {
        MaintenanceEntity entity = new MaintenanceEntity();
        entity.setId(dto.getId());
        entity.setMaintenanceType(dto.getMaintenanceType());
        entity.setStartDate(dto.getStartDate());
        entity.setDuration(dto.getDuration());
        entity.setDescription(dto.getDescription());
        entity.setPriority(dto.getPriority());
        entity.setActive(dto.getActive() == null || dto.getActive());
        return entity;
    }

    private MaintenanceDTO mapToDTO(MaintenanceEntity entity) {
        MaintenanceDTO dto = new MaintenanceDTO();
        dto.setId(entity.getId());
        dto.setPhysicalAreaId(entity.getPhysicalArea().getId());
        dto.setMaintenanceType(entity.getMaintenanceType());
        dto.setStartDate(entity.getStartDate());
        dto.setDuration(entity.getDuration());
        dto.setDescription(entity.getDescription());
        dto.setPriority(entity.getPriority());
        dto.setActive(entity.getActive());
        return dto;
    }
}
