package co.edu.uceva.serviciosGenerales.controller;

import co.edu.uceva.serviciosGenerales.service.MantenimientoAsignacionService;
import co.edu.uceva.serviciosGenerales.service.model.dto.MantenimientoAsignacionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mantenimiento-asignaciones")
public class MantenimientoAsignacionController {

    private final MantenimientoAsignacionService mantenimientoAsignacionService;

    public MantenimientoAsignacionController(MantenimientoAsignacionService mantenimientoAsignacionService) {
        this.mantenimientoAsignacionService = mantenimientoAsignacionService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<MantenimientoAsignacionDTO>> listarMantenimientoAsignaciones() {
        return ResponseEntity.ok(mantenimientoAsignacionService.listarMantenimientosAsignaciones());
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<MantenimientoAsignacionDTO> obtenerMantenimientoAsignacionPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mantenimientoAsignacionService.obtenerMantenimientoAsignacionPorId(id));
    }

    @PostMapping("/create")
    public ResponseEntity<MantenimientoAsignacionDTO> crearMantenimientoAsignacion(
            @RequestBody MantenimientoAsignacionDTO dto) {
        return new ResponseEntity<>(mantenimientoAsignacionService.crearMantenimientoAsignacion(dto),
                HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<MantenimientoAsignacionDTO> actualizarMantenimientoAsignacion(@PathVariable Long id,
            @RequestBody MantenimientoAsignacionDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(mantenimientoAsignacionService.actualizarMantenimientoAsignacion(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarMantenimientoAsignacion(@PathVariable Long id) {
        mantenimientoAsignacionService.eliminarMantenimientoAsignacion(id);
        return ResponseEntity.noContent().build();
    }
}
