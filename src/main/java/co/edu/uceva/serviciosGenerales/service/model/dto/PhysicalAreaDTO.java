package co.edu.uceva.serviciosGenerales.service.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Data Transfer Object (DTO) para representar un área física.
 * Proporciona validaciones y estructura para manejar datos en las operaciones
 * de servicio.
 */
@Data
public class PhysicalAreaDTO {

    /**
     * Identificador único del área física.
     */
    private Long id;

    /**
     * Nombre del área física.
     * Este campo es obligatorio.
     */
    @NotBlank(message = "The name is required")
    private String name;

    /**
     * Ubicación del área física.
     * Este campo es obligatorio.
     */
    @NotBlank(message = "The location is required")
    private String location;

    /**
     * Descripción del área física.
     * Este campo es obligatorio.
     */
    @NotBlank(message = "The description is required")
    private String description;

    /**
     * Cantidad de incidencias asociadas al área física.
     * Si no se especifica, tendrá un valor predeterminado de 0 en la base de datos.
     */
    private Long incidentCount;

    /**
     * Estado de actividad del área física.
     * Si no se especifica, será true (activo) en la base de datos.
     * Si es false, se considera inactivo.
     */
    private Boolean active;
}
