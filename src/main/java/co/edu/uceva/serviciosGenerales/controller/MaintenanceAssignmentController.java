package co.edu.uceva.serviciosGenerales.controller;

import co.edu.uceva.serviciosGenerales.exception.UnauthorizedException;
import co.edu.uceva.serviciosGenerales.service.MaintenanceAssignmentService;
import co.edu.uceva.serviciosGenerales.service.impl.JWTUtilityServiceImpl;
import co.edu.uceva.serviciosGenerales.service.model.dto.MaintenanceAssignmentDTO;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nimbusds.jose.JOSEException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

/**
 * Controlador REST para la gestión de asignaciones de mantenimiento.
 * Proporciona endpoints para crear, actualizar, listar y eliminar asignaciones.
 */
@RestController
@RequestMapping("/api/v1/maintenance-assignments")
public class MaintenanceAssignmentController {

    private final MaintenanceAssignmentService maintenanceAssignmentService;
    private final JWTUtilityServiceImpl jwtUtilityService;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String ROLE_ADMIN = "administrador";
    private static final String ROLE_SERVICIOS_GENERALES = "servicios_generales";

    public MaintenanceAssignmentController(MaintenanceAssignmentService maintenanceAssignmentService,
            JWTUtilityServiceImpl jwtUtilityService) {
        this.maintenanceAssignmentService = maintenanceAssignmentService;
        this.jwtUtilityService = jwtUtilityService;
    }

    private void validateRole(HttpServletRequest request, String... allowedRoles)
            throws InvalidKeySpecException, NoSuchAlgorithmException, ParseException, JOSEException, IOException {
        String token = request.getHeader(AUTHORIZATION_HEADER).substring(7);
        String userRole = jwtUtilityService.extractRoleFromJWT(token);

        if (!Arrays.asList(allowedRoles).contains(userRole)) {
            throw new UnauthorizedException("Access denied: insufficient permissions");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<MaintenanceAssignmentDTO>> listMaintenanceAssignments(HttpServletRequest request)
            throws InvalidKeySpecException, NoSuchAlgorithmException, ParseException, JOSEException, IOException {
        validateRole(request, ROLE_SERVICIOS_GENERALES, ROLE_ADMIN);
        return ResponseEntity.ok(maintenanceAssignmentService.listMaintenanceAssignments());
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<MaintenanceAssignmentDTO> getMaintenanceAssignmentById(@PathVariable Long id,
            HttpServletRequest request)
            throws InvalidKeySpecException, NoSuchAlgorithmException, ParseException, JOSEException, IOException {
        validateRole(request, ROLE_SERVICIOS_GENERALES, ROLE_ADMIN);
        return ResponseEntity.ok(maintenanceAssignmentService.getMaintenanceAssignmentById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<MaintenanceAssignmentDTO> createMaintenanceAssignment(
            @RequestBody MaintenanceAssignmentDTO dto, HttpServletRequest request)
            throws InvalidKeySpecException, NoSuchAlgorithmException, ParseException, JOSEException, IOException {
        validateRole(request, ROLE_ADMIN);
        return new ResponseEntity<>(maintenanceAssignmentService.createMaintenanceAssignment(dto), HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<MaintenanceAssignmentDTO> updateMaintenanceAssignment(@PathVariable Long id,
            @RequestBody MaintenanceAssignmentDTO dto, HttpServletRequest request)
            throws InvalidKeySpecException, NoSuchAlgorithmException, ParseException, JOSEException, IOException {
        validateRole(request, ROLE_ADMIN, ROLE_SERVICIOS_GENERALES);
        dto.setId(id);
        return ResponseEntity.ok(maintenanceAssignmentService.updateMaintenanceAssignment(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMaintenanceAssignment(@PathVariable Long id, HttpServletRequest request)
            throws InvalidKeySpecException, NoSuchAlgorithmException, ParseException, JOSEException, IOException {
        validateRole(request, ROLE_ADMIN);
        maintenanceAssignmentService.deleteMaintenanceAssignment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list/assigned-to-me")
    public ResponseEntity<List<MaintenanceAssignmentDTO>> listAssignedMaintenances(HttpServletRequest request)
            throws Exception {
        // Extraer el token del encabezado Authorization
        String token = request.getHeader(AUTHORIZATION_HEADER).substring(7);

        // Extraer el ID del usuario desde el token
        Long userId = Long.parseLong(jwtUtilityService.extractUserIdFromJWT(token));

        // Obtener las asignaciones de mantenimiento del usuario
        List<MaintenanceAssignmentDTO> assignedMaintenances = maintenanceAssignmentService
                .listAssignmentsForUser(userId);

        // Retornar las asignaciones de mantenimiento
        return ResponseEntity.ok(assignedMaintenances);
    }

}
