package co.edu.uceva.serviciosGenerales.controller;

import co.edu.uceva.serviciosGenerales.service.IncidentService;
import co.edu.uceva.serviciosGenerales.service.impl.JWTUtilityServiceImpl;
import co.edu.uceva.serviciosGenerales.service.model.dto.IncidentDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de incidencias.
 * Proporciona endpoints para crear, actualizar, listar y eliminar incidencias.
 */
@RestController
@RequestMapping("/api/v1/incidents")
public class IncidentController {

    private final IncidentService incidentService;
    private final JWTUtilityServiceImpl jwtUtilityService;

    public IncidentController(IncidentService incidentService, JWTUtilityServiceImpl jwtUtilityService) {
        this.incidentService = incidentService;
        this.jwtUtilityService = jwtUtilityService;
    }

    // Crear incidencia (accesible para todos los usuarios)
    @PostMapping("/create")
    public ResponseEntity<IncidentDTO> createIncident(@Valid @RequestBody IncidentDTO incidentDTO,
            HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = Long.parseLong(jwtUtilityService.extractUserIdFromJWT(token)); // Extrae el ID del usuario desde
                                                                                     // el JWT

        incidentDTO.setUserId(userId); // Asocia la incidencia al usuario autenticado
        IncidentDTO created = incidentService.createIncident(incidentDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Listar incidencias del usuario autenticado
    @GetMapping("/list/my-incidents")
    public ResponseEntity<List<IncidentDTO>> listMyIncidents(HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = Long.parseLong(jwtUtilityService.extractUserIdFromJWT(token)); // Extrae el ID del usuario desde
                                                                                     // el JWT

        List<IncidentDTO> myIncidents = incidentService.listIncidentsByUserId(userId);
        return ResponseEntity.ok(myIncidents);
    }

    // Obtener una incidencia por su ID (usuarios autenticados pueden acceder a las
    // suyas, roles especiales pueden acceder a todas)
    @GetMapping("/list/{id}")
    public ResponseEntity<IncidentDTO> getIncidentById(@PathVariable Long id, HttpServletRequest request)
            throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = Long.parseLong(jwtUtilityService.extractUserIdFromJWT(token)); // Extrae el ID del usuario desde
                                                                                     // el JWT
        String userRole = jwtUtilityService.extractRoleFromJWT(token); // Extrae el rol del usuario desde el JWT

        IncidentDTO incident = incidentService.getIncidentById(id);

        // Verifica si el usuario tiene permiso para acceder a la incidencia
        if (!incident.getUserId().equals(userId) &&
                !(userRole.equals("servicios_generales") || userRole.equals("administrador"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(incident);
    }

    // Listar todas las incidencias (solo para servicios generales y
    // administradores)
    @GetMapping("/list")
    public ResponseEntity<List<IncidentDTO>> listAllIncidents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String userRole = jwtUtilityService.extractRoleFromJWT(token);

        // Validar roles
        if (!(userRole.equals("servicios_generales") || userRole.equals("administrador"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Obtener la página de incidentes
        Page<IncidentDTO> incidentsPage = incidentService.listActiveIncidents(page, size);
        List<IncidentDTO> incidents = incidentsPage.getContent();

        return ResponseEntity.ok(incidents);
    }

    // Actualizar una incidencia (solo para servicios generales y administradores)
    @PutMapping("/edit/{id}")
    public ResponseEntity<IncidentDTO> updateIncident(
            @PathVariable Long id,
            @Valid @RequestBody IncidentDTO incidentDTO,
            HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String userRole = jwtUtilityService.extractRoleFromJWT(token);

        if (!(userRole.equals("servicios_generales") || userRole.equals("administrador"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        IncidentDTO updated = incidentService.updateIncident(id, incidentDTO);
        return ResponseEntity.ok(updated);
    }

    // Eliminar una incidencia (solo para servicios generales y administradores)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String userRole = jwtUtilityService.extractRoleFromJWT(token);

        if (!(userRole.equals("servicios_generales") || userRole.equals("administrador"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        incidentService.deleteIncident(id);
        return ResponseEntity.noContent().build();
    }
}
