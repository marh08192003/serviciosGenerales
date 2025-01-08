package co.edu.uceva.serviciosGenerales.controller;

import co.edu.uceva.serviciosGenerales.exception.UnauthorizedException;
import co.edu.uceva.serviciosGenerales.service.MaintenanceService;
import co.edu.uceva.serviciosGenerales.service.impl.JWTUtilityServiceImpl;
import co.edu.uceva.serviciosGenerales.service.model.dto.MaintenanceDTO;
import jakarta.servlet.http.HttpServletRequest;
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
 * Controlador REST para la gestión de mantenimientos.
 * Proporciona endpoints para crear, actualizar, listar y eliminar
 * mantenimientos.
 */
@RestController
@RequestMapping("/api/v1/maintenances")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;
    private final JWTUtilityServiceImpl jwtUtilityService;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String ROLE_ADMIN = "administrador";
    private static final String ROLE_SERVICIOS_GENERALES = "servicios_generales";

    public MaintenanceController(MaintenanceService maintenanceService, JWTUtilityServiceImpl jwtUtilityService) {
        this.maintenanceService = maintenanceService;
        this.jwtUtilityService = jwtUtilityService;
    }

    private void validateRole(HttpServletRequest request, String... allowedRoles)
            throws InvalidKeySpecException, NoSuchAlgorithmException, ParseException, JOSEException, IOException {
        String token = request.getHeader(AUTHORIZATION_HEADER).substring(7);
        String userRole = jwtUtilityService.extractRoleFromJWT(token);

        if (!List.of(allowedRoles).contains(userRole)) {
            throw new UnauthorizedException("Access denied: insufficient permissions");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<MaintenanceDTO>> listMaintenances(HttpServletRequest request)
            throws InvalidKeySpecException, NoSuchAlgorithmException, ParseException, JOSEException, IOException {
        validateRole(request, ROLE_SERVICIOS_GENERALES, ROLE_ADMIN);
        List<MaintenanceDTO> maintenances = maintenanceService.listMaintenances();
        return ResponseEntity.ok(maintenances);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<MaintenanceDTO> getMaintenanceById(@PathVariable Long id, HttpServletRequest request)
            throws InvalidKeySpecException, NoSuchAlgorithmException, ParseException, JOSEException, IOException {
        validateRole(request, ROLE_SERVICIOS_GENERALES, ROLE_ADMIN);
        return ResponseEntity.ok(maintenanceService.getMaintenanceById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<MaintenanceDTO> createMaintenance(@RequestBody MaintenanceDTO maintenanceDTO,
            HttpServletRequest request)
            throws InvalidKeySpecException, NoSuchAlgorithmException, ParseException, JOSEException, IOException {
        validateRole(request, ROLE_ADMIN);
        MaintenanceDTO created = maintenanceService.createMaintenance(maintenanceDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<MaintenanceDTO> updateMaintenance(@PathVariable Long id,
            @RequestBody MaintenanceDTO maintenanceDTO,
            HttpServletRequest request)
            throws InvalidKeySpecException, NoSuchAlgorithmException, ParseException, JOSEException, IOException {
        validateRole(request, ROLE_ADMIN);
        maintenanceDTO.setId(id);
        return ResponseEntity.ok(maintenanceService.updateMaintenance(maintenanceDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable Long id, HttpServletRequest request)
            throws InvalidKeySpecException, NoSuchAlgorithmException, ParseException, JOSEException, IOException {
        validateRole(request, ROLE_ADMIN);
        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.noContent().build();
    }
}
