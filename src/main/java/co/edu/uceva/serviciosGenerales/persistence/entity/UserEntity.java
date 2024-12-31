package co.edu.uceva.serviciosGenerales.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user") // Tabla de usuarios (Users Table)
public class UserEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id; // CC o algún otro identificador

    @Column(name = "first_name", nullable = false, length = 50) // Nombre del usuario
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50) // Apellido del usuario
    private String lastName;

    @Column(name = "phone", length = 15) // Teléfono del usuario
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false) // Tipo de usuario
    private UserType userType;

    @Column(name = "institutional_email", nullable = false, unique = true, length = 100) // Correo institucional del usuario
    private String institutionalEmail;

    @Column(name = "password", nullable = false, length = 255) // Contraseña del usuario
    private String password;

    /**
     * Indicador de 'activo'. Si es false, se considera "eliminado" (soft delete).
     */
    @Column(name = "active", nullable = false)
    private Boolean active = true;
}
