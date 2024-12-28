package co.edu.uceva.serviciosGenerales.service.impl;

import co.edu.uceva.serviciosGenerales.exception.ResourceNotFoundException;
import co.edu.uceva.serviciosGenerales.persistence.entity.EstadoIncidencia;
import co.edu.uceva.serviciosGenerales.persistence.entity.IncidenciaEntity;
import co.edu.uceva.serviciosGenerales.persistence.entity.UsuarioEntity;
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

    private final IncidenciaRepository incidenciaRepository;
    private final UsuarioRepository usuarioRepository;

    public IncidenciaServiceImpl(IncidenciaRepository incidenciaRepository, UsuarioRepository usuarioRepository) {
        this.incidenciaRepository = incidenciaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public IncidenciaDTO crearIncidencia(IncidenciaDTO incidenciaDTO) {
        UsuarioEntity usuario = usuarioRepository.findByIdAndActivoTrue(incidenciaDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException(USUARIO_NO_ENCONTRADO));

        IncidenciaEntity incidencia = new IncidenciaEntity();
        incidencia.setUsuario(usuario);
        incidencia.setDescripcion(incidenciaDTO.getDescripcion());
        incidencia.setUbicacion(incidenciaDTO.getUbicacion());
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

        incidencia.setDescripcion(incidenciaDTO.getDescripcion());
        incidencia.setUbicacion(incidenciaDTO.getUbicacion());
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
                .toList(); // Reemplazo de Collectors.toList() por toList()
    }

    @Override
    public void eliminarIncidencia(Long id) {
        IncidenciaEntity incidencia = incidenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(INCIDENCIA_NO_ENCONTRADA));

        incidencia.setActivo(false);
        incidenciaRepository.save(incidencia);
    }

    private IncidenciaDTO mapToDTO(IncidenciaEntity entity) {
        IncidenciaDTO dto = new IncidenciaDTO();
        dto.setId(entity.getId());
        dto.setUsuarioId(entity.getUsuario().getId());
        dto.setDescripcion(entity.getDescripcion());
        dto.setFechaReporte(entity.getFechaReporte());
        dto.setEstado(entity.getEstado());
        dto.setUbicacion(entity.getUbicacion());
        dto.setActivo(entity.getActivo());
        return dto;
    }
}