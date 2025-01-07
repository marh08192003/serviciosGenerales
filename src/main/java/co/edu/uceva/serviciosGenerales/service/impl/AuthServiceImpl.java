package co.edu.uceva.serviciosGenerales.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.uceva.serviciosGenerales.persistence.entity.UserEntity;
import co.edu.uceva.serviciosGenerales.persistence.repository.UserRepository;
import co.edu.uceva.serviciosGenerales.service.IAuthService;
import co.edu.uceva.serviciosGenerales.service.IJWTUtilityService;
import co.edu.uceva.serviciosGenerales.service.model.dto.LoginDTO;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Data
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;
    private final IJWTUtilityService jwtUtilityService;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public HashMap<String, String> login(LoginDTO loginRequest) {
        try {
            HashMap<String, String> jwt = new HashMap<>();
            Optional<UserEntity> user = userRepository.findByEmail(loginRequest.getEmail());

            if (user.isEmpty()) {
                jwt.put("error", "User not registered");
                logger.warn("Login attempt with unregistered email: {}", loginRequest.getEmail());
                return jwt;
            }

            if (verifyPassword(loginRequest.getPassword(), user.get().getPassword())) {
                jwt.put("jwt", jwtUtilityService.generateJWT(user.get().getId(), user.get().getUserType().toString()));
                jwt.put("firstName", user.get().getFirstName());
                jwt.put("email", user.get().getInstitutionalEmail());
                jwt.put("id", user.get().getId().toString());
                jwt.put("userType", user.get().getUserType().toString());
                logger.info("User {} logged in successfully", loginRequest.getEmail());
            } else {
                jwt.put("error", "Authentication failed");
                logger.warn("Failed login attempt for user: {}", loginRequest.getEmail());
            }
            return jwt;

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error generating JWT", e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unknown error", e);
        }
    }

    @Override
    public HashMap<String, String> register(UserEntity user){
        HashMap<String, String> response = new HashMap<>();
        try{
            if (userRepository.findByEmail(user.getInstitutionalEmail()).isPresent()) {
                response.put("error", "User with this email already exist!");
                logger.warn("Attempt to register existing user: {}", user.getInstitutionalEmail());
                return response;
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);

            response.put("id", user.getId().toString());
            response.put("firstName", user.getFirstName());
            response.put("lastName", user.getLastName());
            response.put("phone", user.getPhone());
            response.put("userType", user.getUserType().toString());
            response.put("institutionalEmail", user.getInstitutionalEmail());

            logger.info("User {} registered successfully with JWT", user.getInstitutionalEmail());
            
        }catch (Exception e) {
            throw new IllegalArgumentException("Error during registration", e);
        }
        return response;
    }

    private boolean verifyPassword(String enteredPassword, String storedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword, storedPassword);
    }

}
