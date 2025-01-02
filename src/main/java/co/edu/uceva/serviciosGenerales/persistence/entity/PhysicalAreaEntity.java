package co.edu.uceva.serviciosGenerales.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entidad que representa un área física en el sistema.
 * Permite gestionar información como el nombre, ubicación,
 * descripción, cantidad de incidencias y estado de actividad.
 */
@Data
@Entity
@Table(name = "physical_area")
public class PhysicalAreaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String location;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "incident_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Long incidentCount = 0L;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active = true;

    @OneToMany(mappedBy = "physicalArea", fetch = FetchType.LAZY)
    private List<IncidentEntity> incidents = new ArrayList<>();

    @OneToMany(mappedBy = "physicalArea", fetch = FetchType.LAZY)
    private List<MaintenanceEntity> maintenances = new ArrayList<>();
}
