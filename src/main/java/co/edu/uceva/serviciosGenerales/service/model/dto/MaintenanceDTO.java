package co.edu.uceva.serviciosGenerales.service.model.dto;

import co.edu.uceva.serviciosGenerales.persistence.entity.MaintenancePriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO para representar un mantenimiento.
 */
@Data
public class MaintenanceDTO {

    @NotNull(message = "El ID no puede ser nulo")
    private Long id;

    @NotNull(message = "El área física es obligatoria")
    private Long physicalAreaId;

    @NotBlank(message = "El tipo de mantenimiento es obligatorio")
    private String maintenanceType;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime startDate;

    @NotNull(message = "La duración es obligatoria")
    private Integer duration;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    private MaintenancePriority priority = MaintenancePriority.media;

    private Boolean active = true;
}
