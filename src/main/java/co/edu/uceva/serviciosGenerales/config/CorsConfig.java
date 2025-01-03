package co.edu.uceva.serviciosGenerales.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // Permite todos los orígenes temporalmente
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*") // Permite todos los encabezados
                .allowCredentials(true) // Permite el uso de credenciales
                .maxAge(3600); // Cache para preflight requests

        registry.addMapping("/auth/**")
                .allowedOriginPatterns("*") // Permite todos los orígenes temporalmente
                .allowedMethods("OPTIONS", "POST")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
