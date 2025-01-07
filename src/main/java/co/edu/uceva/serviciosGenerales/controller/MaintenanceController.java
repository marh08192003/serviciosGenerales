package co.edu.uceva.serviciosGenerales.controller;

import co.edu.uceva.serviciosGenerales.service.MaintenanceService;
import co.edu.uceva.serviciosGenerales.service.impl.JWTUtilityServiceImpl;
import co.edu.uceva.serviciosGenerales.service.model.dto.MaintenanceDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    public MaintenanceController(MaintenanceService maintenanceService, JWTUtilityServiceImpl jwtUtilityService) {
        this.maintenanceService = maintenanceService;
        this.jwtUtilityService = jwtUtilityService;
    }

    /**
     * Endpoint para listar todos los mantenimientos activos.
     * 
     * @return Lista de mantenimientos activos.
     */
    @GetMapping("/list")
    public ResponseEntity<List<MaintenanceDTO>> listMaintenances(HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String userRole = jwtUtilityService.extractRoleFromJWT(token);

        if (!(userRole.equals("servicios_generales") || userRole.equals("administrador"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null); // Devuelve una lista vacía con código 403
        }

        List<MaintenanceDTO> maintenances = maintenanceService.listMaintenances();
        return ResponseEntity.ok(maintenances);
    }

    /**
     * Endpoint para obtener un mantenimiento por su ID.
     * 
     * @param id ID del mantenimiento.
     * @return El mantenimiento encontrado.
     */
    @GetMapping("/list/{id}")
    public ResponseEntity<MaintenanceDTO> getMaintenanceById(@PathVariable Long id, HttpServletRequest request)
            throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String userRole = jwtUtilityService.extractRoleFromJWT(token);

        if (!(userRole.equals("servicios_generales") || userRole.equals("administrador"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null); // Devuelve null con código 403
        }

        return ResponseEntity.ok(maintenanceService.getMaintenanceById(id));
    }

    /**
     * Endpoint para crear un nuevo mantenimiento.
     * 
     * @param maintenanceDTO Datos del mantenimiento a crear.
     * @return El mantenimiento creado.
     */
    @PostMapping("/create")
    public ResponseEntity<MaintenanceDTO> createMaintenance(@RequestBody MaintenanceDTO maintenanceDTO,
            HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String userRole = jwtUtilityService.extractRoleFromJWT(token);

        if (!userRole.equals("administrador")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null); // Devuelve null con código 403
        }

        MaintenanceDTO created = maintenanceService.createMaintenance(maintenanceDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * Endpoint para actualizar un mantenimiento existente.
     * 
     * @param id             ID del mantenimiento a actualizar.
     * @param maintenanceDTO Datos actualizados del mantenimiento.
     * @return El mantenimiento actualizado.
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity<MaintenanceDTO> updateMaintenance(@PathVariable Long id,
            @RequestBody MaintenanceDTO maintenanceDTO,
            HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String userRole = jwtUtilityService.extractRoleFromJWT(token);

        if (!userRole.equals("administrador")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null); // Devuelve null con código 403
        }

        maintenanceDTO.setId(id);
        return ResponseEntity.ok(maintenanceService.updateMaintenance(maintenanceDTO));
    }

    /**
     * Endpoint para eliminar (soft delete) un mantenimiento por su ID.
     * 
     * @param id ID del mantenimiento a eliminar.
     * @return Respuesta HTTP 204 (sin contenido).
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable Long id, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String userRole = jwtUtilityService.extractRoleFromJWT(token);

        if (!userRole.equals("administrador")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.noContent().build();
    }
}
