package co.edu.uceva.serviciosGenerales.service;

import java.util.HashMap;

import co.edu.uceva.serviciosGenerales.persistence.entity.UserEntity;
import co.edu.uceva.serviciosGenerales.service.model.dto.LoginDTO;

public interface IAuthService {
    public HashMap<String, String> login(LoginDTO loginRequest);

    public HashMap<String, String> register(UserEntity user);

}
