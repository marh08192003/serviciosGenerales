package co.edu.uceva.serviciosGenerales.security;

import co.edu.uceva.serviciosGenerales.service.IJWTUtilityService;
import co.edu.uceva.serviciosGenerales.service.impl.JWTUtilityServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

        private final IJWTUtilityService jwtUtilityService; // Declarar el campo como final

        @Autowired
        public SecurityConfiguration(IJWTUtilityService jwtUtilityService) {
                this.jwtUtilityService = jwtUtilityService; // Inicialización a través del constructor
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(authRequest -> authRequest
                                                .requestMatchers("/auth/**").permitAll() // Permitir acceso sin
                                                                                         // autenticación a /auth/**
                                                .requestMatchers(HttpMethod.GET, "/api/v1/physical-areas/list")
                                                .hasAnyAuthority("estudiante", "profesor", "administrador",
                                                                "servicios_generales")
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Permitir
                                                                                                        // solicitudes
                                                                                                        // OPTIONS
                                                                                                        // (CORS)
                                                .anyRequest().authenticated()) // Requiere autenticación para todas las
                                                                               // demás solicitudes
                                .sessionManagement(sessionManager -> sessionManager
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(new JWTAuthorizationFilter((JWTUtilityServiceImpl) jwtUtilityService),
                                                UsernamePasswordAuthenticationFilter.class)
                                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(
                                                (request, response, authException) -> response.sendError(
                                                                HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")))
                                .build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
