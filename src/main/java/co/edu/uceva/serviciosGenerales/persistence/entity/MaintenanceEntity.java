package co.edu.uceva.serviciosGenerales.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entidad que representa un mantenimiento registrado en el sistema.
 */
@Data
@Entity
@Table(name = "maintenance")
public class MaintenanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "physical_area_id", nullable = false)
    private PhysicalAreaEntity physicalArea;

    @Column(name = "maintenance_type", nullable = false, length = 100)
    private String maintenanceType;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private MaintenancePriority priority = MaintenancePriority.media;

    @Column(name = "active", nullable = false)
    private Boolean active = true;
}
