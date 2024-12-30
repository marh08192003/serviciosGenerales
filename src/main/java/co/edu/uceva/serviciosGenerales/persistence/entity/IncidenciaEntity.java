package co.edu.uceva.serviciosGenerales.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "incidencia")
public class IncidenciaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "area_fisica_id", nullable = false)
    private AreaFisicaEntity areaFisica;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_reporte", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaReporte = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, columnDefinition = "ENUM('pendiente', 'en_proceso', 'resuelta')")
    private EstadoIncidencia estado = EstadoIncidencia.pendiente;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}
