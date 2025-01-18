package co.edu.uceva.serviciosGenerales.service.impl;

import co.edu.uceva.serviciosGenerales.exception.ResourceNotFoundException;
import co.edu.uceva.serviciosGenerales.exception.ResourceAlreadyInactiveException;
import co.edu.uceva.serviciosGenerales.persistence.entity.PhysicalAreaEntity;
import co.edu.uceva.serviciosGenerales.persistence.repository.PhysicalAreaRepository;
import co.edu.uceva.serviciosGenerales.service.PhysicalAreaService;
import co.edu.uceva.serviciosGenerales.service.model.dto.PhysicalAreaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementation of the PhysicalAreaService interface.
 * Provides methods to manage physical areas, including creation, updating,
 * retrieval, listing active areas, and soft deletion.
 */
@Service
@Transactional
public class PhysicalAreaServiceImpl implements PhysicalAreaService {

    private static final String AREA_NOT_FOUND_MESSAGE = "Physical area not found";
    private static final String AREA_ALREADY_INACTIVE_MESSAGE = "The physical area is already inactive or deleted.";

    private final PhysicalAreaRepository physicalAreaRepository;

    public PhysicalAreaServiceImpl(PhysicalAreaRepository physicalAreaRepository) {
        this.physicalAreaRepository = physicalAreaRepository;
    }

    @Override
    public PhysicalAreaDTO createPhysicalArea(PhysicalAreaDTO physicalAreaDTO) {
        PhysicalAreaEntity entity = mapToEntity(physicalAreaDTO);
        PhysicalAreaEntity savedEntity = physicalAreaRepository.save(entity);
        return mapToDTO(savedEntity);
    }

    @Override
    public PhysicalAreaDTO updatePhysicalArea(PhysicalAreaDTO physicalAreaDTO) {
        PhysicalAreaEntity existingArea = physicalAreaRepository.findById(physicalAreaDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException(AREA_NOT_FOUND_MESSAGE));

        existingArea.setName(physicalAreaDTO.getName());
        existingArea.setLocation(physicalAreaDTO.getLocation());
        existingArea.setDescription(physicalAreaDTO.getDescription());

        PhysicalAreaEntity updatedEntity = physicalAreaRepository.save(existingArea);
        return mapToDTO(updatedEntity);
    }

    @Override
    public PhysicalAreaDTO getPhysicalAreaById(Long id) {
        PhysicalAreaEntity entity = physicalAreaRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(AREA_NOT_FOUND_MESSAGE));
        return mapToDTO(entity);
    }

    @Override
    public Page<PhysicalAreaDTO> listActivePhysicalAreas(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PhysicalAreaEntity> physicalAreaPage = physicalAreaRepository.findAllByActiveTrue(pageable);
        return physicalAreaPage.map(this::mapToDTO);
    }

    @Override
    public void deletePhysicalArea(Long id) {
        PhysicalAreaEntity entity = physicalAreaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AREA_NOT_FOUND_MESSAGE));

        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new ResourceAlreadyInactiveException(AREA_ALREADY_INACTIVE_MESSAGE);
        }
        entity.setActive(false);
        physicalAreaRepository.save(entity);
    }

    // -------------------------------------------------------------------------------------------
    // Private mapping methods
    // -------------------------------------------------------------------------------------------

    private PhysicalAreaEntity mapToEntity(PhysicalAreaDTO dto) {
        PhysicalAreaEntity entity = new PhysicalAreaEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setLocation(dto.getLocation());
        entity.setDescription(dto.getDescription());
        entity.setIncidentCount(dto.getIncidentCount() == null ? 0 : dto.getIncidentCount());
        entity.setActive(dto.getActive() == null || dto.getActive());
        return entity;
    }

    private PhysicalAreaDTO mapToDTO(PhysicalAreaEntity entity) {
        PhysicalAreaDTO dto = new PhysicalAreaDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLocation(entity.getLocation());
        dto.setDescription(entity.getDescription());
        dto.setIncidentCount(entity.getIncidentCount());
        dto.setActive(entity.getActive());
        return dto;
    }
}
