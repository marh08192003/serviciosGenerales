package co.edu.uceva.serviciosGenerales.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

import com.nimbusds.jose.JOSEException;

import co.edu.uceva.serviciosGenerales.persistence.entity.UserEntity;
import co.edu.uceva.serviciosGenerales.service.model.dto.LoginDTO;

public interface IAuthService {
    public HashMap<String, String> login(LoginDTO loginRequest)
            throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, JOSEException;

    public HashMap<String, String> register(UserEntity user);

}
