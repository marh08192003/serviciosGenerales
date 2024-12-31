package co.edu.uceva.serviciosGenerales.controller;

import co.edu.uceva.serviciosGenerales.service.IncidentService;
import co.edu.uceva.serviciosGenerales.service.model.dto.IncidentDTO;
import jakarta.validation.Valid;
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

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    /**
     * Endpoint para crear una nueva incidencia.
     *
     * @param incidentDTO Datos de la incidencia a crear.
     * @return Incidencia creada con estado HTTP 201.
     */
    @PostMapping("/create")
    public ResponseEntity<IncidentDTO> createIncident(@Valid @RequestBody IncidentDTO incidentDTO) {
        IncidentDTO created = incidentService.createIncident(incidentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Endpoint para actualizar una incidencia existente.
     *
     * @param id          ID de la incidencia a actualizar.
     * @param incidentDTO Datos de la incidencia actualizados.
     * @return Incidencia actualizada.
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity<IncidentDTO> updateIncident(
            @PathVariable Long id,
            @Valid @RequestBody IncidentDTO incidentDTO) {
        IncidentDTO updated = incidentService.updateIncident(id, incidentDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * Endpoint para obtener una incidencia por su ID.
     *
     * @param id ID de la incidencia a buscar.
     * @return Incidencia encontrada.
     */
    @GetMapping("/list/{id}")
    public ResponseEntity<IncidentDTO> getIncidentById(@PathVariable Long id) {
        IncidentDTO incident = incidentService.getIncidentById(id);
        return ResponseEntity.ok(incident);
    }

    /**
     * Endpoint para listar todas las incidencias activas.
     *
     * @return Lista de incidencias activas.
     */
    @GetMapping("/list")
    public ResponseEntity<List<IncidentDTO>> listActiveIncidents() {
        List<IncidentDTO> incidents = incidentService.listActiveIncidents();
        return ResponseEntity.ok(incidents);
    }

    /**
     * Endpoint para listar incidencias activas por área física.
     *
     * @param physicalAreaId ID del área física.
     * @return Lista de incidencias activas relacionadas con el área física.
     */
    @GetMapping("/list/area/{physicalAreaId}")
    public ResponseEntity<List<IncidentDTO>> listIncidentsByPhysicalArea(@PathVariable Long physicalAreaId) {
        List<IncidentDTO> incidents = incidentService.listIncidentsByPhysicalArea(physicalAreaId);
        return ResponseEntity.ok(incidents);
    }

    /**
     * Endpoint para eliminar (soft delete) una incidencia.
     *
     * @param id ID de la incidencia a eliminar.
     * @return Respuesta HTTP 204 (sin contenido).
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        incidentService.deleteIncident(id);
        return ResponseEntity.noContent().build();
    }
}
