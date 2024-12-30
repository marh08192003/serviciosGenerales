package co.edu.uceva.serviciosGenerales.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "mantenimiento")
public class MantenimientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "area_fisica_id", nullable = false)
    private AreaFisicaEntity areaFisica;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @Column(name = "tipo_mantenimiento", nullable = false, length = 100)
    private String tipoMantenimiento;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "duracion", nullable = false)
    private Integer duracion; // Duración en horas

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", nullable = false)
    private PrioridadMantenimiento prioridad = PrioridadMantenimiento.media;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}
