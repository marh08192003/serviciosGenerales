package co.edu.uceva.serviciosGenerales.service.model.dto;

import co.edu.uceva.serviciosGenerales.persistence.entity.MaintenancePriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) para representar un mantenimiento.
 * Proporciona validaciones y estructura para manejar datos en las operaciones
 * de servicio.
 */
@Data
public class MaintenanceDTO {

    /**
     * Identificador único del mantenimiento.
     * Este campo es obligatorio.
     */
    @NotNull(message = "El ID no puede ser nulo")
    private Long id;

    /**
     * Identificador del área física asociada al mantenimiento.
     * Este campo es obligatorio.
     */
    @NotNull(message = "El área física es obligatoria")
    private Long physicalAreaId;

    /**
     * Identificador del usuario que programa el mantenimiento.
     * Este campo es obligatorio.
     */
    @NotNull(message = "El usuario es obligatorio")
    private Long userId;

    /**
     * Tipo de mantenimiento (e.g., preventivo, correctivo).
     * Este campo es obligatorio.
     */
    @NotBlank(message = "El tipo de mantenimiento es obligatorio")
    private String maintenanceType;

    /**
     * Fecha y hora de inicio del mantenimiento.
     * Este campo es obligatorio.
     */
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime startDate;

    /**
     * Duración del mantenimiento en horas.
     * Este campo es obligatorio.
     */
    @NotNull(message = "La duración es obligatoria")
    private Integer duration;

    /**
     * Descripción del mantenimiento.
     * Este campo es obligatorio.
     */
    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    /**
     * Prioridad del mantenimiento (baja, media, alta).
     * El valor predeterminado es media.
     */
    private MaintenancePriority priority = MaintenancePriority.media;

    /**
     * Indica si el mantenimiento está activo.
     */
    private Boolean active = true;
}