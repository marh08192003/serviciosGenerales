package co.edu.uceva.serviciosGenerales.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uceva.serviciosGenerales.exception.UserAlreadyInactiveException;
import co.edu.uceva.serviciosGenerales.exception.UserNotFoundException;
import co.edu.uceva.serviciosGenerales.persistence.entity.UserEntity;
import co.edu.uceva.serviciosGenerales.persistence.repository.UserRepository;
import co.edu.uceva.serviciosGenerales.service.UserService;
import co.edu.uceva.serviciosGenerales.service.model.dto.UserDTO;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND_MESSAGE = "User not found";
    private static final String USER_ALREADY_INACTIVE_MESSAGE = "The user is already inactive or deleted.";

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity entity = mapToEntity(userDTO);
        UserEntity savedEntity = userRepository.save(entity);
        return mapToDTO(savedEntity);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        // To update, allow the user to exist (active or not).
        // If you want to enforce that the user must be "active" to update,
        // use findByIdAndActiveTrue(...) here instead.
        UserEntity existingUser = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setPhone(userDTO.getPhone());
        existingUser.setUserType(userDTO.getUserType());
        existingUser.setInstitutionalEmail(userDTO.getInstitutionalEmail());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setActive(userDTO.getActive());

        UserEntity updatedEntity = userRepository.save(existingUser);
        return mapToDTO(updatedEntity);
    }

    /**
     * Now uses findByIdAndActiveTrue: throws exception if the user is not active.
     */
    @Override
    public UserDTO getUserById(Long id) {
        UserEntity entity = userRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
        return mapToDTO(entity);
    }

    /**
     * Lists only users where active = true.
     */
    @Override
    public List<UserDTO> listUsers() {
        List<UserEntity> activeUsers = userRepository.findByActiveTrue();
        return activeUsers.stream()
                .map(this::mapToDTO)
                .toList(); // Available in Java 16+
    }

    @Override
    public void deleteUser(Long id) {
        // Soft delete: sets 'active' to false
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new UserAlreadyInactiveException(USER_ALREADY_INACTIVE_MESSAGE);
        }
        entity.setActive(false);
        userRepository.save(entity);
    }

    // -------------------------------------------------------------------------------------------
    // Private mapping methods
    // -------------------------------------------------------------------------------------------

    private UserEntity mapToEntity(UserDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhone(dto.getPhone());
        entity.setUserType(dto.getUserType());
        entity.setInstitutionalEmail(dto.getInstitutionalEmail());
        entity.setPassword(dto.getPassword());
        // If null => true
        entity.setActive(dto.getActive() == null || dto.getActive());
        return entity;
    }

    private UserDTO mapToDTO(UserEntity entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhone(entity.getPhone());
        dto.setUserType(entity.getUserType());
        dto.setInstitutionalEmail(entity.getInstitutionalEmail());
        dto.setPassword(entity.getPassword());
        dto.setActive(entity.getActive());
        return dto;
    }
}
