package co.edu.uceva.serviciosGenerales.controller;

import co.edu.uceva.serviciosGenerales.service.MaintenanceService;
import co.edu.uceva.serviciosGenerales.service.model.dto.MaintenanceDTO;
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

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    /**
     * Endpoint para listar todos los mantenimientos activos.
     * 
     * @return Lista de mantenimientos activos.
     */
    @GetMapping("/list")
    public ResponseEntity<List<MaintenanceDTO>> listMaintenances() {
        return ResponseEntity.ok(maintenanceService.listMaintenances());
    }

    /**
     * Endpoint para obtener un mantenimiento por su ID.
     * 
     * @param id ID del mantenimiento.
     * @return El mantenimiento encontrado.
     */
    @GetMapping("/list/{id}")
    public ResponseEntity<MaintenanceDTO> getMaintenanceById(@PathVariable Long id) {
        return ResponseEntity.ok(maintenanceService.getMaintenanceById(id));
    }

    /**
     * Endpoint para crear un nuevo mantenimiento.
     * 
     * @param maintenanceDTO Datos del mantenimiento a crear.
     * @return El mantenimiento creado.
     */
    @PostMapping("/create")
    public ResponseEntity<MaintenanceDTO> createMaintenance(@RequestBody MaintenanceDTO maintenanceDTO) {
        return new ResponseEntity<>(maintenanceService.createMaintenance(maintenanceDTO), HttpStatus.CREATED);
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
            @RequestBody MaintenanceDTO maintenanceDTO) {
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
    public ResponseEntity<Void> deleteMaintenance(@PathVariable Long id) {
        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.noContent().build();
    }
}
