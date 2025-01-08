package co.edu.uceva.serviciosGenerales.controller;

import co.edu.uceva.serviciosGenerales.exception.UnauthorizedException;
import co.edu.uceva.serviciosGenerales.service.PhysicalAreaService;
import co.edu.uceva.serviciosGenerales.service.impl.JWTUtilityServiceImpl;
import co.edu.uceva.serviciosGenerales.service.model.dto.PhysicalAreaDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
     * Método para validar el rol del usuario.
     *
     * @param request      La solicitud HTTP.
     * @param allowedRoles Roles permitidos para el acceso.
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
     * Endpoint para listar todas las áreas físicas activas.
     *
     * @return Una lista de áreas físicas activas.
     */
    @GetMapping("/list")
    public ResponseEntity<List<PhysicalAreaDTO>> listPhysicalAreas() {
        List<PhysicalAreaDTO> physicalAreas = physicalAreaService.listActivePhysicalAreas();
        return ResponseEntity.ok(physicalAreas);
    }

    /**
     * Endpoint para obtener un área física por su ID.
     *
     * @param id El identificador único del área física.
     * @return El área física encontrada.
     */
    @GetMapping("/list/{id}")
    public ResponseEntity<PhysicalAreaDTO> getPhysicalAreaById(@PathVariable Long id) {
        PhysicalAreaDTO physicalArea = physicalAreaService.getPhysicalAreaById(id);
        return ResponseEntity.ok(physicalArea);
    }

    /**
     * Endpoint para crear una nueva área física.
     *
     * @param physicalAreaDTO Los detalles del área física a crear.
     * @param request         La solicitud HTTP.
     * @return El área física creada.
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
     * Endpoint para actualizar un área física existente.
     *
     * @param id              El identificador único del área física a actualizar.
     * @param physicalAreaDTO Los detalles actualizados del área física.
     * @param request         La solicitud HTTP.
     * @return El área física actualizada.
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
     * Endpoint para eliminar (soft delete) un área física por su ID.
     *
     * @param id      El identificador único del área física a eliminar.
     * @param request La solicitud HTTP.
     * @return Una respuesta indicando que la eliminación fue exitosa.
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
