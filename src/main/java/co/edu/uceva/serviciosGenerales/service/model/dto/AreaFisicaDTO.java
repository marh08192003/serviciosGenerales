package co.edu.uceva.serviciosGenerales.service.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AreaFisicaDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La ubicacion es obligatoria")
    private String ubicacion;

    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    /**
     * Si no se especifica, en la BD será 0 (por defecto).
     */
    private Long cantidadIncidencias;

    /**
     * Si es null => en la BD quedará como true (por defecto).
     * Si es false => se considera inactivo.
     */
    private Boolean activo;
}
