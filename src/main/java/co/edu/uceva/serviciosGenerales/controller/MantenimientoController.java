package co.edu.uceva.serviciosGenerales.controller;

import co.edu.uceva.serviciosGenerales.service.MantenimientoService;
import co.edu.uceva.serviciosGenerales.service.model.dto.MantenimientoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mantenimientos")
public class MantenimientoController {

    private final MantenimientoService mantenimientoService;

    public MantenimientoController(MantenimientoService mantenimientoService) {
        this.mantenimientoService = mantenimientoService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<MantenimientoDTO>> listarMantenimientos() {
        return ResponseEntity.ok(mantenimientoService.listarMantenimientos());
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<MantenimientoDTO> obtenerMantenimientoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mantenimientoService.obtenerMantenimientoPorId(id));
    }

    @PostMapping("/create")
    public ResponseEntity<MantenimientoDTO> crearMantenimiento(@RequestBody MantenimientoDTO mantenimientoDTO) {
        return new ResponseEntity<>(mantenimientoService.crearMantenimiento(mantenimientoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<MantenimientoDTO> actualizarMantenimiento(@PathVariable Long id,
            @RequestBody MantenimientoDTO mantenimientoDTO) {
        mantenimientoDTO.setId(id);
        return ResponseEntity.ok(mantenimientoService.actualizarMantenimiento(mantenimientoDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarMantenimiento(@PathVariable Long id) {
        mantenimientoService.eliminarMantenimiento(id);
        return ResponseEntity.noContent().build();
    }
}
