package co.edu.uceva.serviciosGenerales.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "mantenimiento_asignacion")
public class MantenimientoAsignacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mantenimiento_id", nullable = false)
    private MantenimientoEntity mantenimiento;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @Column(name = "completado", nullable = false)
    private Boolean completado = false;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}