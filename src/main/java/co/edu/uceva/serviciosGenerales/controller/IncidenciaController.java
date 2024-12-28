package co.edu.uceva.serviciosGenerales.controller;

import co.edu.uceva.serviciosGenerales.service.IncidenciaService;
import co.edu.uceva.serviciosGenerales.service.model.dto.IncidenciaDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/incidencias")
public class IncidenciaController {

    private final IncidenciaService incidenciaService;

    public IncidenciaController(IncidenciaService incidenciaService) {
        this.incidenciaService = incidenciaService;
    }

    /**
     * Endpoint para crear una nueva incidencia.
     *
     * @param incidenciaDTO Datos de la incidencia a crear.
     * @return Incidencia creada con estado HTTP 201.
     */
    @PostMapping("/create")
    public ResponseEntity<IncidenciaDTO> crearIncidencia(@Valid @RequestBody IncidenciaDTO incidenciaDTO) {
        IncidenciaDTO creada = incidenciaService.crearIncidencia(incidenciaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    /**
     * Endpoint para actualizar una incidencia existente.
     *
     * @param id            ID de la incidencia a actualizar.
     * @param incidenciaDTO Datos de la incidencia actualizados.
     * @return Incidencia actualizada.
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity<IncidenciaDTO> actualizarIncidencia(
            @PathVariable Long id,
            @Valid @RequestBody IncidenciaDTO incidenciaDTO) {
        IncidenciaDTO actualizada = incidenciaService.actualizarIncidencia(id, incidenciaDTO);
        return ResponseEntity.ok(actualizada);
    }

    /**
     * Endpoint para obtener una incidencia por su ID.
     *
     * @param id ID de la incidencia a buscar.
     * @return Incidencia encontrada.
     */
    @GetMapping("/list/{id}")
    public ResponseEntity<IncidenciaDTO> obtenerIncidenciaPorId(@PathVariable Long id) {
        IncidenciaDTO incidencia = incidenciaService.obtenerIncidenciaPorId(id);
        return ResponseEntity.ok(incidencia);
    }

    /**
     * Endpoint para listar todas las incidencias activas.
     *
     * @return Lista de incidencias activas.
     */
    @GetMapping("/list")
    public ResponseEntity<List<IncidenciaDTO>> listarIncidenciasActivas() {
        List<IncidenciaDTO> incidencias = incidenciaService.listarIncidenciasActivas();
        return ResponseEntity.ok(incidencias);
    }

    /**
     * Endpoint para eliminar (soft delete) una incidencia.
     *
     * @param id ID de la incidencia a eliminar.
     * @return Respuesta HTTP 204 (sin contenido).
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarIncidencia(@PathVariable Long id) {
        incidenciaService.eliminarIncidencia(id);
        return ResponseEntity.noContent().build();
    }
}
