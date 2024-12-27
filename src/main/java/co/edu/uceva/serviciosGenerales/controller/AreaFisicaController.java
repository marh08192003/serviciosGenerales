package co.edu.uceva.serviciosGenerales.controller;

import co.edu.uceva.serviciosGenerales.service.AreaFisicaService;
import co.edu.uceva.serviciosGenerales.service.model.dto.AreaFisicaDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/areas-fisicas")
public class AreaFisicaController {

    private final AreaFisicaService areaFisicaService;

    public AreaFisicaController(AreaFisicaService areaFisicaService) {
        this.areaFisicaService = areaFisicaService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<AreaFisicaDTO>> listarAreasFisicas() {
        List<AreaFisicaDTO> areasFisicas = areaFisicaService.listarAreasFisicasActivas();
        return ResponseEntity.ok(areasFisicas);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<AreaFisicaDTO> obtenerAreaFisicaPorId(@PathVariable Long id) {
        AreaFisicaDTO areaFisica = areaFisicaService.obtenerAreaFisicaPorId(id);
        return ResponseEntity.ok(areaFisica);
    }

    @PostMapping("/create")
    public ResponseEntity<AreaFisicaDTO> crearAreaFisica(@Valid @RequestBody AreaFisicaDTO areaFisicaDTO) {
        AreaFisicaDTO creada = areaFisicaService.crearAreaFisica(areaFisicaDTO);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<AreaFisicaDTO> actualizarAreaFisica(
            @PathVariable Long id,
            @Valid @RequestBody AreaFisicaDTO areaFisicaDTO) {

        areaFisicaDTO.setId(id);
        AreaFisicaDTO actualizada = areaFisicaService.actualizarAreaFisica(areaFisicaDTO);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarAreaFisica(@PathVariable Long id) {
        areaFisicaService.eliminarAreaFisica(id);
        return ResponseEntity.noContent().build();
    }
}