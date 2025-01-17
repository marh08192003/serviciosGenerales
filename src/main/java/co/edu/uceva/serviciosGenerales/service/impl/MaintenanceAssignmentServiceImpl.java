package co.edu.uceva.serviciosGenerales.service.impl;

import co.edu.uceva.serviciosGenerales.exception.DuplicateAssignmentException;
import co.edu.uceva.serviciosGenerales.exception.ResourceNotFoundException;
import co.edu.uceva.serviciosGenerales.persistence.entity.MaintenanceAssignmentEntity;
import co.edu.uceva.serviciosGenerales.persistence.entity.MaintenanceEntity;
import co.edu.uceva.serviciosGenerales.persistence.entity.UserEntity;
import co.edu.uceva.serviciosGenerales.persistence.repository.MaintenanceAssignmentRepository;
import co.edu.uceva.serviciosGenerales.persistence.repository.MaintenanceRepository;
import co.edu.uceva.serviciosGenerales.persistence.repository.UserRepository;
import co.edu.uceva.serviciosGenerales.service.MaintenanceAssignmentService;
import co.edu.uceva.serviciosGenerales.service.model.dto.MaintenanceAssignmentDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio de gestión de asignaciones de mantenimiento.
 * Proporciona métodos para crear, actualizar, obtener, listar y eliminar
 * asignaciones.
 */
@Service
@Transactional
public class MaintenanceAssignmentServiceImpl implements MaintenanceAssignmentService {

    private static final String ASSIGNMENT_NOT_FOUND_MESSAGE = "Asignación no encontrada";
    private static final String MAINTENANCE_NOT_FOUND_MESSAGE = "El mantenimiento no está activo o no existe";
    private static final String USER_NOT_FOUND_MESSAGE = "El usuario no está activo o no existe";
    private static final String USER_ASSIGNED_MESSAGE = "El usuario ya está asignado a este mantenimiento.";

    private final MaintenanceAssignmentRepository maintenanceAssignmentRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final UserRepository userRepository;

    public MaintenanceAssignmentServiceImpl(MaintenanceAssignmentRepository maintenanceAssignmentRepository,
            MaintenanceRepository maintenanceRepository,
            UserRepository userRepository) {
        this.maintenanceAssignmentRepository = maintenanceAssignmentRepository;
        this.maintenanceRepository = maintenanceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MaintenanceAssignmentDTO createMaintenanceAssignment(MaintenanceAssignmentDTO dto) {
        MaintenanceEntity maintenance = maintenanceRepository.findByIdAndActiveTrue(dto.getMaintenanceId())
                .orElseThrow(() -> new ResourceNotFoundException(MAINTENANCE_NOT_FOUND_MESSAGE));

        UserEntity user = userRepository.findByIdAndActiveTrue(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_MESSAGE));

        // Validar si ya existe una asignación activa
        boolean exists = maintenanceAssignmentRepository
                .existsByMaintenanceIdAndUserIdAndActiveTrue(dto.getMaintenanceId(), dto.getUserId());
        if (exists) {
            throw new DuplicateAssignmentException(USER_ASSIGNED_MESSAGE);
        }

        MaintenanceAssignmentEntity entity = new MaintenanceAssignmentEntity();
        entity.setMaintenance(maintenance);
        entity.setUser(user);
        entity.setCompleted(dto.getCompleted());
        entity.setActive(true);

        return mapToDTO(maintenanceAssignmentRepository.save(entity));
    }

    @Override
    public MaintenanceAssignmentDTO updateMaintenanceAssignment(MaintenanceAssignmentDTO dto) {
        MaintenanceAssignmentEntity entity = maintenanceAssignmentRepository.findByIdAndActiveTrue(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ASSIGNMENT_NOT_FOUND_MESSAGE));

        entity.setCompleted(dto.getCompleted());
        return mapToDTO(maintenanceAssignmentRepository.save(entity));
    }

    @Override
    public MaintenanceAssignmentDTO getMaintenanceAssignmentById(Long id) {
        MaintenanceAssignmentEntity entity = maintenanceAssignmentRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(ASSIGNMENT_NOT_FOUND_MESSAGE));
        return mapToDTO(entity);
    }

    @Override
    public List<MaintenanceAssignmentDTO> listMaintenanceAssignments() {
        return maintenanceAssignmentRepository.findByActiveTrue().stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void deleteMaintenanceAssignment(Long id) {
        MaintenanceAssignmentEntity entity = maintenanceAssignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ASSIGNMENT_NOT_FOUND_MESSAGE));
        entity.setActive(false);
        maintenanceAssignmentRepository.save(entity);
    }

    private MaintenanceAssignmentDTO mapToDTO(MaintenanceAssignmentEntity entity) {
        MaintenanceAssignmentDTO dto = new MaintenanceAssignmentDTO();
        dto.setId(entity.getId());
        dto.setMaintenanceId(entity.getMaintenance().getId());
        dto.setUserId(entity.getUser().getId());
        dto.setCompleted(entity.getCompleted());
        dto.setActive(entity.getActive());
        return dto;
    }

    @Override
    public List<MaintenanceAssignmentDTO> listAssignmentsForUser(Long userId) {
        List<MaintenanceAssignmentEntity> assignments = maintenanceAssignmentRepository
                .findByUserIdAndActiveTrue(userId);

        return assignments.stream()
                .map(this::mapToDTO)
                .toList();
    }

}
