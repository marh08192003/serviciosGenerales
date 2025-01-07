package co.edu.uceva.serviciosGenerales.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

public interface IJWTUtilityService {

    public JWTClaimsSet parseJWT(String jwt)
            throws JOSEException, IOException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException;

    public String generateJWT(Long userId, String userType)
            throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, JOSEException;


}