package co.edu.uceva.serviciosGenerales.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @SuppressWarnings("null")
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (registry == null) {
            throw new IllegalArgumentException("CorsRegistry no puede ser nulo");
        }

        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);

        registry.addMapping("/auth/**")
                .allowedOriginPatterns("*")
                .allowedMethods("OPTIONS", "POST")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

}
