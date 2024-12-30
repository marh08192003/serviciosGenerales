package co.edu.uceva.serviciosGenerales.service.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MantenimientoAsignacionDTO {

    private Long id;

    @NotNull(message = "El ID del mantenimiento no puede ser nulo")
    private Long mantenimientoId;

    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Long usuarioId;

    private Boolean completado = false;

    private Boolean activo = true;
}