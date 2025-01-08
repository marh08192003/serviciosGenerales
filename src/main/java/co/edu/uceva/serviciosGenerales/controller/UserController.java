package co.edu.uceva.serviciosGenerales.controller;

import co.edu.uceva.serviciosGenerales.service.UserService;
import co.edu.uceva.serviciosGenerales.service.model.dto.UserDTO;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Obtener lista de usuarios
    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> listUsers() {
        List<UserDTO> users = userService.listUsers();
        return ResponseEntity.ok(users);
    }

    // Obtener un usuario por ID
    @GetMapping("/list/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // Crear un nuevo usuario
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(
            @Validated({ UserDTO.Create.class, Default.class }) @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Actualizar un usuario existente
    @PutMapping("/edit/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @Validated({ UserDTO.Update.class, Default.class }) @RequestBody UserDTO userDTO) {
        userDTO.setId(id); // Asegurarse de que el ID del path coincida con el del objeto
        UserDTO updatedUser = userService.updateUser(userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    // Eliminar un usuario (soft delete)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
