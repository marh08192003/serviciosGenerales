package co.edu.uceva.serviciosGenerales.service.impl;

import co.edu.uceva.serviciosGenerales.exception.ResourceNotFoundException;
import co.edu.uceva.serviciosGenerales.persistence.entity.AreaFisicaEntity;
import co.edu.uceva.serviciosGenerales.persistence.entity.MantenimientoEntity;
import co.edu.uceva.serviciosGenerales.persistence.entity.UsuarioEntity;
import co.edu.uceva.serviciosGenerales.persistence.repository.AreaFisicaRepository;
import co.edu.uceva.serviciosGenerales.persistence.repository.MantenimientoRepository;
import co.edu.uceva.serviciosGenerales.persistence.repository.UsuarioRepository;
import co.edu.uceva.serviciosGenerales.service.MantenimientoService;
import co.edu.uceva.serviciosGenerales.service.model.dto.MantenimientoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MantenimientoServiceImpl implements MantenimientoService {

    private static final String MANTENIMIENTO_NOT_FOUND_MESSAGE = "Mantenimiento no encontrado";

    private final MantenimientoRepository mantenimientoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AreaFisicaRepository areaFisicaRepository;

    public MantenimientoServiceImpl(MantenimientoRepository mantenimientoRepository,
            UsuarioRepository usuarioRepository,
            AreaFisicaRepository areaFisicaRepository) {
        this.mantenimientoRepository = mantenimientoRepository;
        this.usuarioRepository = usuarioRepository;
        this.areaFisicaRepository = areaFisicaRepository;
    }

    @Override
    public MantenimientoDTO crearMantenimiento(MantenimientoDTO mantenimientoDTO) {
        // Validar que el área física esté activa
        AreaFisicaEntity areaFisica = areaFisicaRepository.findByIdAndActivoTrue(mantenimientoDTO.getAreaFisicaId())
                .orElseThrow(() -> new ResourceNotFoundException("El área física no está activa o no existe"));

        // Validar que el usuario esté activo
        UsuarioEntity usuario = usuarioRepository.findByIdAndActivoTrue(mantenimientoDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no está activo o no existe"));

        // Mapear DTO a entidad
        MantenimientoEntity entity = mapToEntity(mantenimientoDTO);
        entity.setAreaFisica(areaFisica);
        entity.setUsuario(usuario);

        // Guardar mantenimiento
        MantenimientoEntity savedEntity = mantenimientoRepository.save(entity);
        return mapToDTO(savedEntity);
    }

    @Override
    public MantenimientoDTO actualizarMantenimiento(MantenimientoDTO mantenimientoDTO) {
        MantenimientoEntity existingEntity = mantenimientoRepository.findByIdAndActivoTrue(mantenimientoDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException(MANTENIMIENTO_NOT_FOUND_MESSAGE));

        existingEntity.setTipoMantenimiento(mantenimientoDTO.getTipoMantenimiento());
        existingEntity.setFechaInicio(mantenimientoDTO.getFechaInicio());
        existingEntity.setDuracion(mantenimientoDTO.getDuracion());
        existingEntity.setDescripcion(mantenimientoDTO.getDescripcion());
        existingEntity.setPrioridad(mantenimientoDTO.getPrioridad());

        MantenimientoEntity updatedEntity = mantenimientoRepository.save(existingEntity);
        return mapToDTO(updatedEntity);
    }

    @Override
    public MantenimientoDTO obtenerMantenimientoPorId(Long id) {
        MantenimientoEntity entity = mantenimientoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(MANTENIMIENTO_NOT_FOUND_MESSAGE));
        return mapToDTO(entity);
    }

    @Override
    public List<MantenimientoDTO> listarMantenimientos() {
        List<MantenimientoEntity> entities = mantenimientoRepository.findByActivoTrue();
        return entities.stream().map(this::mapToDTO).toList();
    }

    @Override
    public void eliminarMantenimiento(Long id) {
        MantenimientoEntity entity = mantenimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MANTENIMIENTO_NOT_FOUND_MESSAGE));
        entity.setActivo(false);
        mantenimientoRepository.save(entity);
    }

    // -------------------------------------------------------------------------------------------
    // Métodos privados de mapeo
    // -------------------------------------------------------------------------------------------

    private MantenimientoEntity mapToEntity(MantenimientoDTO dto) {
        MantenimientoEntity entity = new MantenimientoEntity();
        entity.setId(dto.getId());
        entity.setTipoMantenimiento(dto.getTipoMantenimiento());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setDuracion(dto.getDuracion());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPrioridad(dto.getPrioridad());
        entity.setActivo(dto.getActivo() == null || dto.getActivo());
        return entity;
    }

    private MantenimientoDTO mapToDTO(MantenimientoEntity entity) {
        MantenimientoDTO dto = new MantenimientoDTO();
        dto.setId(entity.getId());
        dto.setAreaFisicaId(entity.getAreaFisica().getId());
        dto.setUsuarioId(entity.getUsuario() != null ? entity.getUsuario().getId() : null);
        dto.setTipoMantenimiento(entity.getTipoMantenimiento());
        dto.setFechaInicio(entity.getFechaInicio());
        dto.setDuracion(entity.getDuracion());
        dto.setDescripcion(entity.getDescripcion());
        dto.setPrioridad(entity.getPrioridad());
        dto.setActivo(entity.getActivo());
        return dto;
    }
}
