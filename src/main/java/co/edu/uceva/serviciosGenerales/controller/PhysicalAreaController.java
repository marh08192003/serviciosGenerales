package co.edu.uceva.serviciosGenerales.controller;

import co.edu.uceva.serviciosGenerales.service.PhysicalAreaService;
import co.edu.uceva.serviciosGenerales.service.impl.JWTUtilityServiceImpl;
import co.edu.uceva.serviciosGenerales.service.model.dto.PhysicalAreaDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing physical areas.
 * Provides endpoints for CRUD operations and querying active physical areas.
 */
@RestController
@RequestMapping("/api/v1/physical-areas")
public class PhysicalAreaController {

    private final PhysicalAreaService physicalAreaService;

    private final JWTUtilityServiceImpl jwtUtilityService;

    public PhysicalAreaController(PhysicalAreaService physicalAreaService, JWTUtilityServiceImpl jwtUtilityService) {
        this.physicalAreaService = physicalAreaService;
        this.jwtUtilityService = jwtUtilityService;
    }

    /**
     * Endpoint to list all active physical areas.
     * 
     * @return A list of active physical areas.
     */
    @GetMapping("/list")
    public ResponseEntity<List<PhysicalAreaDTO>> listPhysicalAreas() {
        List<PhysicalAreaDTO> physicalAreas = physicalAreaService.listActivePhysicalAreas();
        return ResponseEntity.ok(physicalAreas);
    }

    /**
     * Endpoint to retrieve a physical area by its ID.
     * 
     * @param id The unique identifier of the physical area.
     * @return The physical area if found.
     */
    @GetMapping("/list/{id}")
    public ResponseEntity<PhysicalAreaDTO> getPhysicalAreaById(@PathVariable Long id) {
        PhysicalAreaDTO physicalArea = physicalAreaService.getPhysicalAreaById(id);
        return ResponseEntity.ok(physicalArea);
    }

    /**
     * Endpoint to create a new physical area.
     * 
     * @param physicalAreaDTO The details of the physical area to create.
     * @return The created physical area.
     */
    @PostMapping("/create")
    public ResponseEntity<PhysicalAreaDTO> createPhysicalArea(@Valid @RequestBody PhysicalAreaDTO physicalAreaDTO,
            HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String userRole = jwtUtilityService.extractRoleFromJWT(token);

        if (!userRole.equals("administrador")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null); // Mensaje opcional: "Acceso denegado: Solo los administradores pueden crear
                                 // áreas físicas."
        }

        PhysicalAreaDTO created = physicalAreaService.createPhysicalArea(physicalAreaDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * Endpoint to update an existing physical area.
     * 
     * @param id              The unique identifier of the physical area to update.
     * @param physicalAreaDTO The updated details of the physical area.
     * @return The updated physical area.
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity<PhysicalAreaDTO> updatePhysicalArea(
            @PathVariable Long id,
            @Valid @RequestBody PhysicalAreaDTO physicalAreaDTO,
            HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String userRole = jwtUtilityService.extractRoleFromJWT(token);

        if (!userRole.equals("administrador")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null); // Mensaje opcional: "Acceso denegado: Solo los administradores pueden
                                 // actualizar áreas físicas."
        }

        physicalAreaDTO.setId(id);
        PhysicalAreaDTO updated = physicalAreaService.updatePhysicalArea(physicalAreaDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * Endpoint to delete (soft delete) a physical area by its ID.
     * 
     * @param id The unique identifier of the physical area to delete.
     * @return A response indicating the deletion was successful.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePhysicalArea(@PathVariable Long id, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String userRole = jwtUtilityService.extractRoleFromJWT(token);

        if (!userRole.equals("administrador")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Mensaje opcional: "Acceso denegado: Solo los
                                                                        // administradores pueden eliminar áreas
                                                                        // físicas."
        }

        physicalAreaService.deletePhysicalArea(id);
        return ResponseEntity.noContent().build();
    }

}
