package co.edu.uceva.serviciosGenerales.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entidad que representa un mantenimiento registrado en el sistema.
 * Contiene información sobre el área física, el usuario que programa el
 * mantenimiento,
 * tipo de mantenimiento, fecha de inicio, duración, descripción, prioridad y
 * estado.
 */
@Data
@Entity
@Table(name = "maintenance")
public class MaintenanceEntity {

    /**
     * Identificador único del mantenimiento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Área física asociada al mantenimiento.
     */
    @ManyToOne
    @JoinColumn(name = "physical_area_id", nullable = false)
    private PhysicalAreaEntity physicalArea;

    /**
     * Usuario que programa el mantenimiento.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    /**
     * Tipo de mantenimiento (e.g., preventivo, correctivo).
     */
    @Column(name = "maintenance_type", nullable = false, length = 100)
    private String maintenanceType;

    /**
     * Fecha y hora de inicio del mantenimiento.
     */
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    /**
     * Duración del mantenimiento en horas.
     */
    @Column(name = "duration", nullable = false)
    private Integer duration;

    /**
     * Descripción detallada del mantenimiento.
     */
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * Prioridad del mantenimiento (baja, media, alta).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private MaintenancePriority priority = MaintenancePriority.media;

    /**
     * Indica si el mantenimiento está activo.
     */
    @Column(name = "active", nullable = false)
    private Boolean active = true;
}
