package co.edu.uceva.serviciosGenerales.service.impl;

import co.edu.uceva.serviciosGenerales.exception.ResourceNotFoundException;
import co.edu.uceva.serviciosGenerales.persistence.entity.PhysicalAreaEntity;
import co.edu.uceva.serviciosGenerales.persistence.entity.MaintenanceEntity;
import co.edu.uceva.serviciosGenerales.persistence.repository.PhysicalAreaRepository;
import co.edu.uceva.serviciosGenerales.persistence.repository.MaintenanceRepository;
import co.edu.uceva.serviciosGenerales.service.MaintenanceService;
import co.edu.uceva.serviciosGenerales.service.model.dto.MaintenanceDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de gestión de mantenimientos.
 */
@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    private static final String MAINTENANCE_NOT_FOUND_MESSAGE = "Mantenimiento no encontrado";

    private final MaintenanceRepository maintenanceRepository;
    private final PhysicalAreaRepository physicalAreaRepository;

    public MaintenanceServiceImpl(MaintenanceRepository maintenanceRepository,
            PhysicalAreaRepository physicalAreaRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.physicalAreaRepository = physicalAreaRepository;
    }

    @Override
    public MaintenanceDTO createMaintenance(MaintenanceDTO maintenanceDTO) {
        PhysicalAreaEntity physicalArea = physicalAreaRepository
                .findByIdAndActiveTrue(maintenanceDTO.getPhysicalAreaId())
                .orElseThrow(() -> new ResourceNotFoundException("El área física no está activa o no existe"));

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
    public List<MaintenanceDTO> listMaintenances() {
        List<MaintenanceEntity> entities = maintenanceRepository.findByActiveTrue();
        return entities.stream().map(this::mapToDTO).toList();
    }

    @Override
    public void deleteMaintenance(Long id) {
        MaintenanceEntity entity = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MAINTENANCE_NOT_FOUND_MESSAGE));
        entity.setActive(false);
        maintenanceRepository.save(entity);
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
