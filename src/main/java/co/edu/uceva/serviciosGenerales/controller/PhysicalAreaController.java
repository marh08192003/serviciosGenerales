package co.edu.uceva.serviciosGenerales.controller;

import co.edu.uceva.serviciosGenerales.exception.UnauthorizedException;
import co.edu.uceva.serviciosGenerales.service.PhysicalAreaService;
import co.edu.uceva.serviciosGenerales.service.impl.JWTUtilityServiceImpl;
import co.edu.uceva.serviciosGenerales.service.model.dto.PhysicalAreaDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nimbusds.jose.JOSEException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
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

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String ROLE_ADMIN = "administrador";

    public PhysicalAreaController(PhysicalAreaService physicalAreaService, JWTUtilityServiceImpl jwtUtilityService) {
        this.physicalAreaService = physicalAreaService;
        this.jwtUtilityService = jwtUtilityService;
    }

    /**
     * Validate the user's role.
     *
     * @param request      The HTTP request.
     * @param allowedRoles Allowed roles for access.
     * @throws IOException
     * @throws JOSEException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private void validateRole(HttpServletRequest request, String... allowedRoles)
            throws InvalidKeySpecException, NoSuchAlgorithmException, ParseException, JOSEException, IOException {
        String token = request.getHeader(AUTHORIZATION_HEADER).substring(7);
        String userRole = jwtUtilityService.extractRoleFromJWT(token);

        if (!List.of(allowedRoles).contains(userRole)) {
            throw new UnauthorizedException("Access denied: insufficient permissions");
        }
    }

    /**
     * Endpoint to list all active physical areas.
     * Accessible by all authenticated users.
     *
     * @return A list of active physical areas.
     */
    @GetMapping("/list")
    public ResponseEntity<List<PhysicalAreaDTO>> listPhysicalAreas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<PhysicalAreaDTO> physicalAreas = physicalAreaService.listActivePhysicalAreas(page, size);
        return ResponseEntity.ok(physicalAreas.getContent());
    }

    /**
     * Endpoint to get a physical area by its ID.
     * Accessible by all authenticated users.
     *
     * @param id The unique identifier of the physical area.
     * @return The found physical area.
     */
    @GetMapping("/list/{id}")
    public ResponseEntity<PhysicalAreaDTO> getPhysicalAreaById(@PathVariable Long id) {
        PhysicalAreaDTO physicalArea = physicalAreaService.getPhysicalAreaById(id);
        return ResponseEntity.ok(physicalArea);
    }

    /**
     * Endpoint to create a new physical area.
     * Accessible only by administrators.
     *
     * @param physicalAreaDTO The details of the physical area to create.
     * @param request         The HTTP request.
     * @return The created physical area.
     * @throws IOException
     * @throws JOSEException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @PostMapping("/create")
    public ResponseEntity<PhysicalAreaDTO> createPhysicalArea(
            @Valid @RequestBody PhysicalAreaDTO physicalAreaDTO,
            HttpServletRequest request)
            throws InvalidKeySpecException, NoSuchAlgorithmException, ParseException, JOSEException, IOException {
        validateRole(request, ROLE_ADMIN);
        PhysicalAreaDTO created = physicalAreaService.createPhysicalArea(physicalAreaDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * Endpoint to update an existing physical area.
     * Accessible only by administrators.
     *
     * @param id              The unique identifier of the physical area to update.
     * @param physicalAreaDTO The updated details of the physical area.
     * @param request         The HTTP request.
     * @return The updated physical area.
     * @throws IOException
     * @throws JOSEException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity<PhysicalAreaDTO> updatePhysicalArea(
            @PathVariable Long id,
            @Valid @RequestBody PhysicalAreaDTO physicalAreaDTO,
            HttpServletRequest request)
            throws InvalidKeySpecException, NoSuchAlgorithmException, ParseException, JOSEException, IOException {
        validateRole(request, ROLE_ADMIN);
        physicalAreaDTO.setId(id);
        PhysicalAreaDTO updated = physicalAreaService.updatePhysicalArea(physicalAreaDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * Endpoint to delete (soft delete) a physical area by its ID.
     * Accessible only by administrators.
     *
     * @param id      The unique identifier of the physical area to delete.
     * @param request The HTTP request.
     * @return A response indicating successful deletion.
     * @throws IOException
     * @throws JOSEException
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePhysicalArea(@PathVariable Long id, HttpServletRequest request)
            throws InvalidKeySpecException, NoSuchAlgorithmException, ParseException, JOSEException, IOException {
        validateRole(request, ROLE_ADMIN);
        physicalAreaService.deletePhysicalArea(id);
        return ResponseEntity.noContent().build();
    }
}
