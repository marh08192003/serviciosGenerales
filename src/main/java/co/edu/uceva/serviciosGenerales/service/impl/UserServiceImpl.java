package co.edu.uceva.serviciosGenerales.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(12);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity entity = mapToEntity(userDTO);
        UserEntity savedEntity = userRepository.save(entity);
        return mapToDTO(savedEntity);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        UserEntity existingUser = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        // Actualizar campos básicos
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setPhone(userDTO.getPhone());
        existingUser.setUserType(userDTO.getUserType());
        existingUser.setInstitutionalEmail(userDTO.getInstitutionalEmail());

        // Si active es nulo en el DTO, mantener el estado actual del usuario
        if (userDTO.getActive() != null) {
            existingUser.setActive(userDTO.getActive());
        }

        // Actualizar contraseña solo si se proporciona
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        UserEntity updatedEntity = userRepository.save(existingUser);
        return mapToDTO(updatedEntity);
    }

    @Override
    public UserDTO getUserById(Long id) {
        UserEntity entity = userRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
        return mapToDTO(entity);
    }

    @Override
    public List<UserDTO> listUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // Configurar la paginación
        Page<UserEntity> userPage = userRepository.findByActiveTrue(pageable); // Llamar al repositorio con paginación

        // Convertir entidades a DTOs
        return userPage.getContent().stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new UserAlreadyInactiveException(USER_ALREADY_INACTIVE_MESSAGE);
        }
        entity.setActive(false);
        userRepository.save(entity);
    }

    private UserEntity mapToEntity(UserDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhone(dto.getPhone());
        entity.setUserType(dto.getUserType());
        entity.setInstitutionalEmail(dto.getInstitutionalEmail());
        entity.setPassword(dto.getPassword());

        // Si active es null, asignar true por defecto
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
