package co.edu.uceva.serviciosGenerales.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Entidad que representa una incidencia reportada en el sistema.
 * Rastrea detalles como el usuario que la reportó, el área física asociada,
 * descripción, estado y si la incidencia está activa.
 */
@Data
@Entity
@Table(name = "incident")
public class IncidentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "physical_area_id", nullable = false)
    private PhysicalAreaEntity physicalArea;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "report_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime reportDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "ENUM('pendiente', 'en_proceso', 'resuelta')")
    private IncidentStatus status = IncidentStatus.pendiente;

    @Column(name = "active", nullable = false)
    private Boolean active = true;
}
