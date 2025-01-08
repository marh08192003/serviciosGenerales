package co.edu.uceva.serviciosGenerales.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;

import co.edu.uceva.serviciosGenerales.persistence.entity.UserEntity;
import co.edu.uceva.serviciosGenerales.persistence.repository.UserRepository;
import co.edu.uceva.serviciosGenerales.service.IAuthService;
import co.edu.uceva.serviciosGenerales.service.IJWTUtilityService;
import co.edu.uceva.serviciosGenerales.service.model.dto.LoginDTO;
import lombok.Data;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Data
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;
    private final IJWTUtilityService jwtUtilityService;
    private final BCryptPasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    // Constants for response keys
    private static final String ERROR_KEY = "error";
    private static final String JWT_KEY = "jwt";

    public AuthServiceImpl(UserRepository userRepository, IJWTUtilityService jwtUtilityService) {
        this.userRepository = userRepository;
        this.jwtUtilityService = jwtUtilityService;
        this.passwordEncoder = new BCryptPasswordEncoder(12);
    }

    @Override
    public HashMap<String, String> login(LoginDTO loginRequest)
            throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, JOSEException {
        HashMap<String, String> response = new HashMap<>();

        Optional<UserEntity> user = userRepository.findByEmail(loginRequest.getEmail());
        if (user.isEmpty()) {
            response.put(ERROR_KEY, "User not registered");
            logger.warn("Login attempt with unregistered email: {}", loginRequest.getEmail());
            return response;
        }

        if (verifyPassword(loginRequest.getPassword(), user.get().getPassword())) {
            populateSuccessfulLoginResponse(response, user.get());
            logger.info("User {} logged in successfully", loginRequest.getEmail());
        } else {
            response.put(ERROR_KEY, "Authentication failed");
            logger.warn("Failed login attempt for user: {}", loginRequest.getEmail());
        }

        return response;
    }

    @Override
    public HashMap<String, String> register(UserEntity user) {
        HashMap<String, String> response = new HashMap<>();

        if (userRepository.findByEmail(user.getInstitutionalEmail()).isPresent()) {
            response.put(ERROR_KEY, "User with this email already exists!");
            logger.warn("Attempt to register existing user: {}", user.getInstitutionalEmail());
            return response;
        }

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            populateRegistrationResponse(response, user);
            logger.info("User {} registered successfully", user.getInstitutionalEmail());
        } catch (Exception e) {
            logger.error("Error during registration: {}", e.getMessage());
            response.put(ERROR_KEY, "Error during registration");
        }

        return response;
    }

    private boolean verifyPassword(String enteredPassword, String storedPassword) {
        return passwordEncoder.matches(enteredPassword, storedPassword);
    }

    private void populateSuccessfulLoginResponse(HashMap<String, String> response, UserEntity user)
            throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, JOSEException {
        response.put(JWT_KEY, jwtUtilityService.generateJWT(user.getId(), user.getUserType().toString()));
        response.put("firstName", user.getFirstName());
        response.put("email", user.getInstitutionalEmail());
        response.put("id", user.getId().toString());
        response.put("userType", user.getUserType().toString());
    }

    private void populateRegistrationResponse(HashMap<String, String> response, UserEntity user) {
        response.put("id", user.getId().toString());
        response.put("firstName", user.getFirstName());
        response.put("lastName", user.getLastName());
        response.put("phone", user.getPhone());
        response.put("userType", user.getUserType().toString());
        response.put("institutionalEmail", user.getInstitutionalEmail());
    }
}
