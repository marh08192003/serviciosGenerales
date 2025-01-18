package co.edu.uceva.serviciosGenerales.service;

import co.edu.uceva.serviciosGenerales.service.model.dto.UserDTO;
import java.util.List;

public interface UserService {

    /**
     * Creates a new user and returns it as a DTO.
     */
    UserDTO createUser(UserDTO userDTO);

    /**
     * Updates an existing user (using its ID in the DTO).
     */
    UserDTO updateUser(UserDTO userDTO);

    /**
     * Retrieves a user by its ID.
     */
    UserDTO getUserById(Long id);

    /**
     * Lists all users (active and inactive).
     * If you want only active users, you could create another method.
     */
    List<UserDTO> listUsers(int page, int size);

    /**
     * Deactivates (soft delete) a user by setting its 'active' attribute to false.
     */
    void deleteUser(Long id);
}
