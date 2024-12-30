package co.edu.uceva.serviciosGenerales.service.impl;

import co.edu.uceva.serviciosGenerales.exception.ResourceNotFoundException;
import co.edu.uceva.serviciosGenerales.persistence.entity.MantenimientoAsignacionEntity;
import co.edu.uceva.serviciosGenerales.persistence.entity.MantenimientoEntity;
import co.edu.uceva.serviciosGenerales.persistence.entity.UsuarioEntity;
import co.edu.uceva.serviciosGenerales.persistence.repository.MantenimientoAsignacionRepository;
import co.edu.uceva.serviciosGenerales.persistence.repository.MantenimientoRepository;
import co.edu.uceva.serviciosGenerales.persistence.repository.UsuarioRepository;
import co.edu.uceva.serviciosGenerales.service.MantenimientoAsignacionService;
import co.edu.uceva.serviciosGenerales.service.model.dto.MantenimientoAsignacionDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MantenimientoAsignacionServiceImpl implements MantenimientoAsignacionService {

    private static final String ASIGNACION_NOT_FOUND_MESSAGE = "Asignación no encontrada";
    private static final String MANTENIMIENTO_NOT_FOUND_MESSAGE = "El mantenimiento no está activo o no existe";
    private static final String USUARIO_NOT_FOUND_MESSAGE = "El usuario no está activo o no existe";

    private final MantenimientoAsignacionRepository mantenimientoAsignacionRepository;
    private final MantenimientoRepository mantenimientoRepository;
    private final UsuarioRepository usuarioRepository;

    public MantenimientoAsignacionServiceImpl(MantenimientoAsignacionRepository mantenimientoAsignacionRepository,
            MantenimientoRepository mantenimientoRepository,
            UsuarioRepository usuarioRepository) {
        this.mantenimientoAsignacionRepository = mantenimientoAsignacionRepository;
        this.mantenimientoRepository = mantenimientoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public MantenimientoAsignacionDTO crearMantenimientoAsignacion(MantenimientoAsignacionDTO dto) {
        MantenimientoEntity mantenimiento = mantenimientoRepository.findByIdAndActivoTrue(dto.getMantenimientoId())
                .orElseThrow(() -> new ResourceNotFoundException(MANTENIMIENTO_NOT_FOUND_MESSAGE));

        UsuarioEntity usuario = usuarioRepository.findByIdAndActivoTrue(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException(USUARIO_NOT_FOUND_MESSAGE));

        MantenimientoAsignacionEntity entity = new MantenimientoAsignacionEntity();
        entity.setMantenimiento(mantenimiento);
        entity.setUsuario(usuario);
        entity.setCompletado(dto.getCompletado());
        entity.setActivo(true);

        return mapToDTO(mantenimientoAsignacionRepository.save(entity));
    }

    @Override
    public MantenimientoAsignacionDTO actualizarMantenimientoAsignacion(MantenimientoAsignacionDTO dto) {
        MantenimientoAsignacionEntity entity = mantenimientoAsignacionRepository.findByIdAndActivoTrue(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ASIGNACION_NOT_FOUND_MESSAGE));

        entity.setCompletado(dto.getCompletado());
        return mapToDTO(mantenimientoAsignacionRepository.save(entity));
    }

    @Override
    public MantenimientoAsignacionDTO obtenerMantenimientoAsignacionPorId(Long id) {
        MantenimientoAsignacionEntity entity = mantenimientoAsignacionRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(ASIGNACION_NOT_FOUND_MESSAGE));
        return mapToDTO(entity);
    }

    @Override
    public List<MantenimientoAsignacionDTO> listarMantenimientosAsignaciones() {
        return mantenimientoAsignacionRepository.findByActivoTrue().stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void eliminarMantenimientoAsignacion(Long id) {
        MantenimientoAsignacionEntity entity = mantenimientoAsignacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ASIGNACION_NOT_FOUND_MESSAGE));
        entity.setActivo(false);
        mantenimientoAsignacionRepository.save(entity);
    }

    private MantenimientoAsignacionDTO mapToDTO(MantenimientoAsignacionEntity entity) {
        MantenimientoAsignacionDTO dto = new MantenimientoAsignacionDTO();
        dto.setId(entity.getId());
        dto.setMantenimientoId(entity.getMantenimiento().getId());
        dto.setUsuarioId(entity.getUsuario().getId());
        dto.setCompletado(entity.getCompletado());
        dto.setActivo(entity.getActivo());
        return dto;
    }
}
