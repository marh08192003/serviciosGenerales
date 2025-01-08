package co.edu.uceva.serviciosGenerales.service.model.dto;

import co.edu.uceva.serviciosGenerales.persistence.entity.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {

    public interface Create {
    }

    public interface Update {
    }

    @NotNull(groups = Update.class, message = "The ID cannot be null")
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String phone;

    @NotNull(message = "User type cannot be null")
    private UserType userType;

    @NotBlank(message = "Institutional email is required")
    @Email(message = "A valid email must be provided")
    private String institutionalEmail;

    @NotBlank(groups = Create.class, message = "Password is required for creation")
    private String password;

    /**
     * If null => it will default to true in the database.
     * If false => the user is considered inactive.
     */
    private Boolean active;
}