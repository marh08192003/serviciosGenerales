package co.edu.uceva.serviciosGenerales.service.model.dto;

import co.edu.uceva.serviciosGenerales.persistence.entity.PrioridadMantenimiento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MantenimientoDTO {

    @NotNull(message = "El ID no puede ser nulo")
    private Long id;

    @NotNull(message = "El área física es obligatoria")
    private Long areaFisicaId;

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @NotBlank(message = "El tipo de mantenimiento es obligatorio")
    private String tipoMantenimiento;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime fechaInicio;

    @NotNull(message = "La duración es obligatoria")
    private Integer duracion;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    private PrioridadMantenimiento prioridad = PrioridadMantenimiento.media;

    private Boolean activo = true;
}
