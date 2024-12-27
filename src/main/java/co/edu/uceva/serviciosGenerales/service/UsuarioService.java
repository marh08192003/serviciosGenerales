package co.edu.uceva.serviciosGenerales.service;

import co.edu.uceva.serviciosGenerales.service.model.dto.UsuarioDTO;
import java.util.List;

public interface UsuarioService {

    /**
     * Crea un nuevo usuario y lo devuelve en forma de DTO.
     */
    UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO);

    /**
     * Actualiza un usuario ya existente (usando su ID en el DTO).
     */
    UsuarioDTO actualizarUsuario(UsuarioDTO usuarioDTO);

    /**
     * Obtiene un usuario por su ID.
     */
    UsuarioDTO obtenerUsuarioPorId(Long id);

    /**
     * Lista todos los usuarios (activos e inactivos).
     * Si quieres sólo los activos, podrías crear otro método.
     */
    List<UsuarioDTO> listarUsuarios();

    /**
     * Desactiva (soft delete) un usuario, cambiando su atributo 'activo' a false.
     */
    void eliminarUsuario(Long id);
}
