package co.edu.uceva.serviciosGenerales.service.impl;

import co.edu.uceva.serviciosGenerales.exception.UserAlreadyInactiveException;
import co.edu.uceva.serviciosGenerales.exception.UserNotFoundException;
import co.edu.uceva.serviciosGenerales.persistence.entity.UsuarioEntity;
import co.edu.uceva.serviciosGenerales.persistence.repository.UsuarioRepository;
import co.edu.uceva.serviciosGenerales.service.UsuarioService;
import co.edu.uceva.serviciosGenerales.service.model.dto.UsuarioDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private static final String USER_NOT_FOUND_MESSAGE = "Usuario no encontrado";
    private static final String USER_ALREADY_INACTIVE_MESSAGE = "El usuario ya está inactivo o eliminado.";

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
        UsuarioEntity entity = mapToEntity(usuarioDTO);
        UsuarioEntity savedEntity = usuarioRepository.save(entity);
        return mapToDTO(savedEntity);
    }

    @Override
    public UsuarioDTO actualizarUsuario(UsuarioDTO usuarioDTO) {
        // Para actualizar, permitimos que el usuario exista (activo o no), 
        // pero si quieres forzar que el usuario sea "activo" para permitir la actualización, 
        // usa findByIdAndActivoTrue(...) aquí también.
        UsuarioEntity usuarioExistente = usuarioRepository.findById(usuarioDTO.getId())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        usuarioExistente.setNombre(usuarioDTO.getNombre());
        usuarioExistente.setApellido(usuarioDTO.getApellido());
        usuarioExistente.setTelefono(usuarioDTO.getTelefono());
        usuarioExistente.setTipoUsuario(usuarioDTO.getTipoUsuario());
        usuarioExistente.setCorreoInstitucional(usuarioDTO.getCorreoInstitucional());
        usuarioExistente.setContrasena(usuarioDTO.getContrasena());
        usuarioExistente.setActivo(usuarioDTO.getActivo());

        UsuarioEntity updatedEntity = usuarioRepository.save(usuarioExistente);
        return mapToDTO(updatedEntity);
    }

    /**
     * Ahora usamos findByIdAndActivoTrue: si no está activo, lanzará excepción.
     */
    @Override
    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        UsuarioEntity entity = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
        return mapToDTO(entity);
    }

    /**
     * Ahora se listan solamente los que tienen activo = true
     */
    @Override
    public List<UsuarioDTO> listarUsuarios() {
        List<UsuarioEntity> usuariosActivos = usuarioRepository.findByActivoTrue();
        return usuariosActivos.stream()
                .map(this::mapToDTO)
                .toList(); // Disponible en Java 16+
    }

    @Override
    public void eliminarUsuario(Long id) {
        // Soft delete: cambia 'activo' a false
        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        if (Boolean.FALSE.equals(entity.getActivo())) {
            throw new UserAlreadyInactiveException(USER_ALREADY_INACTIVE_MESSAGE);
        }
        entity.setActivo(false);
        usuarioRepository.save(entity);
    }

    // -------------------------------------------------------------------------------------------
    // Métodos privados de mapeo
    // -------------------------------------------------------------------------------------------

    private UsuarioEntity mapToEntity(UsuarioDTO dto) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setTelefono(dto.getTelefono());
        entity.setTipoUsuario(dto.getTipoUsuario());
        entity.setCorreoInstitucional(dto.getCorreoInstitucional());
        entity.setContrasena(dto.getContrasena());
        // Si es null => true
        entity.setActivo(dto.getActivo() == null || dto.getActivo());
        return entity;
    }

    private UsuarioDTO mapToDTO(UsuarioEntity entity) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setTelefono(entity.getTelefono());
        dto.setTipoUsuario(entity.getTipoUsuario());
        dto.setCorreoInstitucional(entity.getCorreoInstitucional());
        dto.setContrasena(entity.getContrasena());
        dto.setActivo(entity.getActivo());
        return dto;
    }
}
