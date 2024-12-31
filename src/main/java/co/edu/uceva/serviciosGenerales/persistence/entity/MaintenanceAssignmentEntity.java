package co.edu.uceva.serviciosGenerales.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entidad que representa la asignación de un usuario a un mantenimiento.
 * Rastrea información sobre el mantenimiento, el usuario asignado y el estado
 * de la asignación.
 */
@Data
@Entity
@Table(name = "maintenance_assignment")
public class MaintenanceAssignmentEntity {

    /**
     * Identificador único de la asignación.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Mantenimiento asociado a la asignación.
     */
    @ManyToOne
    @JoinColumn(name = "maintenance_id", nullable = false)
    private MaintenanceEntity maintenance;

    /**
     * Usuario asignado al mantenimiento.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    /**
     * Indica si la asignación está completada.
     */
    @Column(name = "completed", nullable = false)
    private Boolean completed = false;

    /**
     * Indica si la asignación está activa.
     */
    @Column(name = "active", nullable = false)
    private Boolean active = true;
}
