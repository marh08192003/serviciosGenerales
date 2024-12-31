package co.edu.uceva.serviciosGenerales.persistence.entity;

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

    /**
     * Identificador único del área física.
     * Generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del área física.
     * Este campo es obligatorio y tiene un máximo de 100 caracteres.
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Ubicación del área física.
     * Campo opcional con un máximo de 255 caracteres.
     */
    @Column(length = 255)
    private String location;

    /**
     * Descripción del área física.
     * Campo opcional que permite almacenar texto extenso.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Cantidad de incidencias reportadas para esta área física.
     * Este campo es obligatorio y tiene un valor predeterminado de 0.
     */
    @Column(name = "incident_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Long incidentCount = 0L;

    /**
     * Estado de actividad del área física.
     * Este campo es obligatorio y tiene un valor predeterminado de true (activo).
     */
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active = true;

}
