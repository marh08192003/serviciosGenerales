package co.edu.uceva.serviciosGenerales.service.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object (DTO) para representar una asignación de mantenimiento.
 * Proporciona validaciones y estructura para manejar datos en las operaciones
 * de servicio.
 */
@Data
public class MaintenanceAssignmentDTO {

    /**
     * Identificador único de la asignación.
     */
    private Long id;

    /**
     * Identificador del mantenimiento asociado.
     * Este campo es obligatorio.
     */
    @NotNull(message = "El ID del mantenimiento no puede ser nulo")
    private Long maintenanceId;

    /**
     * Identificador del usuario asignado al mantenimiento.
     * Este campo es obligatorio.
     */
    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Long userId;

    /**
     * Indica si la asignación está completada.
     */
    private Boolean completed = false;

    /**
     * Indica si la asignación está activa.
     */
    private Boolean active = true;
}
