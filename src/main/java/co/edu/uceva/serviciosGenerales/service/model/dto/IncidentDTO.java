package co.edu.uceva.serviciosGenerales.service.model.dto;

import co.edu.uceva.serviciosGenerales.persistence.entity.IncidentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) para representar una incidencia.
 * Proporciona validaciones y estructura para manejar datos en las operaciones de servicio.
 */
@Data
public class IncidentDTO {

    /**
     * Identificador único de la incidencia.
     */
    private Long id;

    /**
     * Identificador del usuario que reportó la incidencia.
     * Este campo es obligatorio.
     */
    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Long userId;

    /**
     * Identificador del área física asociada a la incidencia.
     * Este campo es obligatorio.
     */
    @NotNull(message = "El ID del área física no puede ser nulo")
    private Long physicalAreaId;

    /**
     * Descripción de la incidencia.
     * Este campo es obligatorio.
     */
    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    /**
     * Fecha y hora en que se reportó la incidencia.
     */
    private LocalDateTime reportDate;

    /**
     * Estado de la incidencia.
     * Valores posibles: pendiente, en_proceso, resuelta.
     */
    private IncidentStatus status = IncidentStatus.pendiente;

    /**
     * Indica si la incidencia está activa.
     */
    private Boolean active = true;
}