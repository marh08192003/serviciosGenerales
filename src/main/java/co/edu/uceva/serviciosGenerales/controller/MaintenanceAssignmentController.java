package co.edu.uceva.serviciosGenerales.controller;

import co.edu.uceva.serviciosGenerales.service.MaintenanceAssignmentService;
import co.edu.uceva.serviciosGenerales.service.impl.JWTUtilityServiceImpl;
import co.edu.uceva.serviciosGenerales.service.model.dto.MaintenanceAssignmentDTO;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    public MaintenanceAssignmentController(MaintenanceAssignmentService maintenanceAssignmentService,
            JWTUtilityServiceImpl jwtUtilityService) {
        this.maintenanceAssignmentService = maintenanceAssignmentService;
        this.jwtUtilityService = jwtUtilityService;
    }

    /**
     * Endpoint para listar todas las asignaciones de mantenimiento activas.
     * 
     * @return Lista de asignaciones activas.
     */
    @GetMapping("/list")
    public ResponseEntity<List<MaintenanceAssignmentDTO>> listMaintenanceAssignments(HttpServletRequest request)
            throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String userRole = jwtUtilityService.extractRoleFromJWT(token);

        if (!(userRole.equals("servicios_generales") || userRole.equals("administrador"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(maintenanceAssignmentService.listMaintenanceAssignments());
    }

    /**
     * Endpoint para obtener una asignación de mantenimiento por su ID.
     * 
     * @param id ID de la asignación.
     * @return La asignación encontrada.
     */
    @GetMapping("/list/{id}")
    public ResponseEntity<MaintenanceAssignmentDTO> getMaintenanceAssignmentById(@PathVariable Long id,
            HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String userRole = jwtUtilityService.extractRoleFromJWT(token);

        if (!(userRole.equals("servicios_generales") || userRole.equals("administrador"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(maintenanceAssignmentService.getMaintenanceAssignmentById(id));
    }

    /**
     * Endpoint para crear una nueva asignación de mantenimiento.
     * 
     * @param dto Datos de la asignación a crear.
     * @return La asignación creada.
     */
    @PostMapping("/create")
    public ResponseEntity<MaintenanceAssignmentDTO> createMaintenanceAssignment(
            @RequestBody MaintenanceAssignmentDTO dto, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String userRole = jwtUtilityService.extractRoleFromJWT(token);

        if (!userRole.equals("administrador")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(maintenanceAssignmentService.createMaintenanceAssignment(dto), HttpStatus.CREATED);
    }

    /**
     * Endpoint para actualizar una asignación de mantenimiento existente.
     * 
     * @param id  ID de la asignación a actualizar.
     * @param dto Datos actualizados de la asignación.
     * @return La asignación actualizada.
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity<MaintenanceAssignmentDTO> updateMaintenanceAssignment(
            @PathVariable Long id,
            @RequestBody MaintenanceAssignmentDTO dto,
            HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String userRole = jwtUtilityService.extractRoleFromJWT(token);

        if (!userRole.equals("administrador")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        dto.setId(id);
        return ResponseEntity.ok(maintenanceAssignmentService.updateMaintenanceAssignment(dto));
    }

    /**
     * Endpoint para eliminar (soft delete) una asignación de mantenimiento por su
     * ID.
     * 
     * @param id ID de la asignación a eliminar.
     * @return Respuesta HTTP 204 (sin contenido).
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMaintenanceAssignment(@PathVariable Long id, HttpServletRequest request)
            throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String userRole = jwtUtilityService.extractRoleFromJWT(token);

        if (!userRole.equals("administrador")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        maintenanceAssignmentService.deleteMaintenanceAssignment(id);
        return ResponseEntity.noContent().build();
    }

}
