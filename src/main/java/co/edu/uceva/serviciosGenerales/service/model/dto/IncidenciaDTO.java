package co.edu.uceva.serviciosGenerales.service.model.dto;

import co.edu.uceva.serviciosGenerales.persistence.entity.EstadoIncidencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IncidenciaDTO {

    private Long id;

    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Long usuarioId;

    @NotNull(message = "El ID del área física no puede ser nulo")
    private Long areaFisicaId;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    private LocalDateTime fechaReporte;

    private EstadoIncidencia estado = EstadoIncidencia.PENDIENTE;

    private Boolean activo = true;
}
