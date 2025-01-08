package co.edu.uceva.serviciosGenerales.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import co.edu.uceva.serviciosGenerales.service.impl.JWTUtilityServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Collections;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final JWTUtilityServiceImpl jwtUtilityService;

    // Constants for error messages
    private static final String ERROR_INVALID_TOKEN = "Invalid or malformed token";
    private static final String ERROR_SECURITY_ISSUE = "Internal security error";

    public JWTAuthorizationFilter(JWTUtilityServiceImpl jwtUtilityService) {
        this.jwtUtilityService = jwtUtilityService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {
            JWTClaimsSet claims = jwtUtilityService.parseJWT(token);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(), null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (JOSEException | ParseException e) {
            handleUnauthorized(response, ERROR_INVALID_TOKEN);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            handleUnauthorized(response, ERROR_SECURITY_ISSUE);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Handles unauthorized requests by setting the response status and message.
     *
     * @param response The HTTP response.
     * @param message  The error message.
     * @throws IOException If an I/O error occurs.
     */
    private void handleUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
        response.getWriter().flush();
    }
}
