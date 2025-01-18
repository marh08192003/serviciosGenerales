package co.edu.uceva.serviciosGenerales.service;

import co.edu.uceva.serviciosGenerales.service.model.dto.PhysicalAreaDTO;

import org.springframework.data.domain.Page;

/**
 * Interface for the Physical Area Service.
 * Defines the contract for operations related to physical areas.
 */
public interface PhysicalAreaService {

    /**
     * Creates a new physical area and returns it as a DTO.
     * 
     * @param physicalAreaDTO Data Transfer Object containing the information of the
     *                        physical area to be created.
     * @return The created physical area as a DTO.
     */
    PhysicalAreaDTO createPhysicalArea(PhysicalAreaDTO physicalAreaDTO);

    /**
     * Updates an existing physical area (using its ID in the DTO).
     * 
     * @param physicalAreaDTO Data Transfer Object containing the updated
     *                        information of the physical area.
     * @return The updated physical area as a DTO.
     */
    PhysicalAreaDTO updatePhysicalArea(PhysicalAreaDTO physicalAreaDTO);

    /**
     * Retrieves a physical area by its ID.
     * 
     * @param id The unique identifier of the physical area.
     * @return The physical area as a DTO if found.
     */
    PhysicalAreaDTO getPhysicalAreaById(Long id);

    /**
     * Lists all active physical areas.
     * 
     * @return A list of active physical areas as DTOs.
     */
    Page<PhysicalAreaDTO> listActivePhysicalAreas(int page, int size);

    /**
     * Soft deletes a physical area by setting its 'active' attribute to false.
     * 
     * @param id The unique identifier of the physical area to be deleted.
     */
    void deletePhysicalArea(Long id);
}