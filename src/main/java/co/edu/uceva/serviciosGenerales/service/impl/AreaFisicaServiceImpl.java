package co.edu.uceva.serviciosGenerales.service.impl;

import co.edu.uceva.serviciosGenerales.exception.ResourceNotFoundException;
import co.edu.uceva.serviciosGenerales.exception.ResourceAlreadyInactiveException;
import co.edu.uceva.serviciosGenerales.persistence.entity.AreaFisicaEntity;
import co.edu.uceva.serviciosGenerales.persistence.repository.AreaFisicaRepository;
import co.edu.uceva.serviciosGenerales.service.AreaFisicaService;
import co.edu.uceva.serviciosGenerales.service.model.dto.AreaFisicaDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AreaFisicaServiceImpl implements AreaFisicaService {

    private static final String AREA_NOT_FOUND_MESSAGE = "Área física no encontrada";
    private static final String AREA_ALREADY_INACTIVE_MESSAGE = "El área física ya está inactiva o eliminada.";

    private final AreaFisicaRepository areaFisicaRepository;

    public AreaFisicaServiceImpl(AreaFisicaRepository areaFisicaRepository) {
        this.areaFisicaRepository = areaFisicaRepository;
    }

    @Override
    public AreaFisicaDTO crearAreaFisica(AreaFisicaDTO areaFisicaDTO) {
        AreaFisicaEntity entity = mapToEntity(areaFisicaDTO);
        AreaFisicaEntity savedEntity = areaFisicaRepository.save(entity);
        return mapToDTO(savedEntity);
    }

    @Override
    public AreaFisicaDTO actualizarAreaFisica(AreaFisicaDTO areaFisicaDTO) {
        AreaFisicaEntity areaExistente = areaFisicaRepository.findById(areaFisicaDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException(AREA_NOT_FOUND_MESSAGE));

        areaExistente.setNombre(areaFisicaDTO.getNombre());
        areaExistente.setUbicacion(areaFisicaDTO.getUbicacion());
        areaExistente.setDescripcion(areaFisicaDTO.getDescripcion());

        AreaFisicaEntity updatedEntity = areaFisicaRepository.save(areaExistente);
        return mapToDTO(updatedEntity);
    }

    @Override
    public AreaFisicaDTO obtenerAreaFisicaPorId(Long id) {
        AreaFisicaEntity entity = areaFisicaRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(AREA_NOT_FOUND_MESSAGE));
        return mapToDTO(entity);
    }

    @Override
    public List<AreaFisicaDTO> listarAreasFisicasActivas() {
        List<AreaFisicaEntity> areasActivas = areaFisicaRepository.findAllByActivoTrue();
        return areasActivas.stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void eliminarAreaFisica(Long id) {
        AreaFisicaEntity entity = areaFisicaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AREA_NOT_FOUND_MESSAGE));

        if (Boolean.FALSE.equals(entity.getActivo())) {
            throw new ResourceAlreadyInactiveException(AREA_ALREADY_INACTIVE_MESSAGE);
        }
        entity.setActivo(false);
        areaFisicaRepository.save(entity);
    }

    // -------------------------------------------------------------------------------------------
    // Métodos privados de mapeo
    // -------------------------------------------------------------------------------------------

    private AreaFisicaEntity mapToEntity(AreaFisicaDTO dto) {
        AreaFisicaEntity entity = new AreaFisicaEntity();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setUbicacion(dto.getUbicacion());
        entity.setDescripcion(dto.getDescripcion());
        entity.setCantidadIncidencias(dto.getCantidadIncidencias() == null ? 0 : dto.getCantidadIncidencias());
        entity.setActivo(dto.getActivo() == null || dto.getActivo());
        return entity;
    }

    private AreaFisicaDTO mapToDTO(AreaFisicaEntity entity) {
        AreaFisicaDTO dto = new AreaFisicaDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setUbicacion(entity.getUbicacion());
        dto.setDescripcion(entity.getDescripcion());
        dto.setCantidadIncidencias(entity.getCantidadIncidencias());
        dto.setActivo(entity.getActivo());
        return dto;
    }
}
