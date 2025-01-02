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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_id", nullable = false)
    private MaintenanceEntity maintenance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "completed", nullable = false)
    private Boolean completed = false;

    @Column(name = "active", nullable = false)
    private Boolean active = true;
}
