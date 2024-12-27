package co.edu.uceva.serviciosGenerales.service.model.dto;

import co.edu.uceva.serviciosGenerales.persistence.entity.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioDTO {

    @NotNull(message = "El ID no puede ser nulo")
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    private String telefono;

    @NotNull(message = "El tipo de usuario no puede ser nulo")
    private TipoUsuario tipoUsuario;

    @NotBlank(message = "El correo institucional es obligatorio")
    @Email(message = "Debe ingresar un correo válido")
    private String correoInstitucional;

    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;

    /**
     * Si es null => en la BD quedará como true (por defecto).
     * Si es false => se considera inactivo.
     */
    private Boolean activo;
}
