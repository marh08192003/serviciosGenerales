package co.edu.uceva.serviciosGenerales.service.impl;

import co.edu.uceva.serviciosGenerales.exception.ResourceNotFoundException;
import co.edu.uceva.serviciosGenerales.persistence.entity.AreaFisicaEntity;
import co.edu.uceva.serviciosGenerales.persistence.entity.EstadoIncidencia;
import co.edu.uceva.serviciosGenerales.persistence.entity.IncidenciaEntity;
import co.edu.uceva.serviciosGenerales.persistence.entity.UsuarioEntity;
import co.edu.uceva.serviciosGenerales.persistence.repository.AreaFisicaRepository;
import co.edu.uceva.serviciosGenerales.persistence.repository.IncidenciaRepository;
import co.edu.uceva.serviciosGenerales.persistence.repository.UsuarioRepository;
import co.edu.uceva.serviciosGenerales.service.IncidenciaService;
import co.edu.uceva.serviciosGenerales.service.model.dto.IncidenciaDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IncidenciaServiceImpl implements IncidenciaService {

    private static final String INCIDENCIA_NO_ENCONTRADA = "Incidencia no encontrada";
    private static final String USUARIO_NO_ENCONTRADO = "Usuario no encontrado o inactivo";
    private static final String AREA_FISICA_NO_ENCONTRADA = "Área física no encontrada o inactiva";

    private final IncidenciaRepository incidenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AreaFisicaRepository areaFisicaRepository;

    public IncidenciaServiceImpl(IncidenciaRepository incidenciaRepository,
            UsuarioRepository usuarioRepository,
            AreaFisicaRepository areaFisicaRepository) {
        this.incidenciaRepository = incidenciaRepository;
        this.usuarioRepository = usuarioRepository;
        this.areaFisicaRepository = areaFisicaRepository;
    }

    @Override
    public IncidenciaDTO crearIncidencia(IncidenciaDTO incidenciaDTO) {
        UsuarioEntity usuario = usuarioRepository.findByIdAndActivoTrue(incidenciaDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException(USUARIO_NO_ENCONTRADO));

        AreaFisicaEntity areaFisica = areaFisicaRepository.findByIdAndActivoTrue(incidenciaDTO.getAreaFisicaId())
                .orElseThrow(() -> new ResourceNotFoundException(AREA_FISICA_NO_ENCONTRADA));

        IncidenciaEntity incidencia = new IncidenciaEntity();
        incidencia.setUsuario(usuario);
        incidencia.setAreaFisica(areaFisica);
        incidencia.setDescripcion(incidenciaDTO.getDescripcion());
        incidencia
                .setEstado(incidenciaDTO.getEstado() != null ? incidenciaDTO.getEstado() : EstadoIncidencia.pendiente);
        incidencia.setActivo(true);

        IncidenciaEntity savedIncidencia = incidenciaRepository.save(incidencia);
        return mapToDTO(savedIncidencia);
    }

    @Override
    public IncidenciaDTO actualizarIncidencia(Long id, IncidenciaDTO incidenciaDTO) {
        IncidenciaEntity incidencia = incidenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(INCIDENCIA_NO_ENCONTRADA));

        if (incidenciaDTO.getAreaFisicaId() != null) {
            AreaFisicaEntity areaFisica = areaFisicaRepository.findByIdAndActivoTrue(incidenciaDTO.getAreaFisicaId())
                    .orElseThrow(() -> new ResourceNotFoundException(AREA_FISICA_NO_ENCONTRADA));
            incidencia.setAreaFisica(areaFisica);
        }

        incidencia.setDescripcion(incidenciaDTO.getDescripcion());
        incidencia.setEstado(incidenciaDTO.getEstado() != null ? incidenciaDTO.getEstado() : incidencia.getEstado());
        incidencia.setActivo(incidenciaDTO.getActivo());

        IncidenciaEntity updatedIncidencia = incidenciaRepository.save(incidencia);
        return mapToDTO(updatedIncidencia);
    }

    @Override
    public IncidenciaDTO obtenerIncidenciaPorId(Long id) {
        IncidenciaEntity incidencia = incidenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(INCIDENCIA_NO_ENCONTRADA));
        return mapToDTO(incidencia);
    }

    @Override
    public List<IncidenciaDTO> listarIncidenciasActivas() {
        return incidenciaRepository.findByActivoTrue().stream()
                .map(this::mapToDTO)
                .toList(); // Java 16+
    }

    @Override
    public void eliminarIncidencia(Long id) {
        IncidenciaEntity incidencia = incidenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(INCIDENCIA_NO_ENCONTRADA));

        incidencia.setActivo(false);
        incidenciaRepository.save(incidencia);
    }

    @Override
    public List<IncidenciaDTO> listarIncidenciasPorAreaFisica(Long areaFisicaId) {
        List<IncidenciaEntity> incidencias = incidenciaRepository.findByAreaFisicaIdAndActivoTrue(areaFisicaId);

        if (incidencias.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No se encontraron incidencias para el área física con ID: " + areaFisicaId);
        }

        return incidencias.stream()
                .map(this::mapToDTO)
                .toList();
    }

    // -------------------------------------------------------------------------------------------
    // Métodos privados de mapeo
    // -------------------------------------------------------------------------------------------

    private IncidenciaDTO mapToDTO(IncidenciaEntity entity) {
        IncidenciaDTO dto = new IncidenciaDTO();
        dto.setId(entity.getId());
        dto.setUsuarioId(entity.getUsuario().getId());
        dto.setAreaFisicaId(entity.getAreaFisica().getId());
        dto.setDescripcion(entity.getDescripcion());
        dto.setFechaReporte(entity.getFechaReporte());
        dto.setEstado(entity.getEstado());
        dto.setActivo(entity.getActivo());
        return dto;
    }
}
