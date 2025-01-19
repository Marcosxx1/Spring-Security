package com.marcos.springsec.config.cors;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CustomCorsConfigurationSource implements CorsConfigurationSource {

    private final Map<String, CorsConfiguration> corsConfigurations = new HashMap<>();

    public CustomCorsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setMaxAge(3600L);

        /*// Caso precisemos utilizar mais que uma configuração do cors
         CorsConfiguration privateConfig = new CorsConfiguration();
        privateConfig.setAllowedOrigins(Arrays.asList("http://private.example.com"));
        privateConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        privateConfig.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        privateConfig.setAllowCredentials(true);
        corsConfigurations.put("/api/private/**", privateConfig);*/

        corsConfigurations.put("/**", config); // Adiciona a configuração ao mapa
    }

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        String requestUri = request.getRequestURI();

        for(Map.Entry<String, CorsConfiguration> entry : corsConfigurations.entrySet()){
            if(requestUri.startsWith(entry.getKey().replace("/**", ""))){
                return entry.getValue();
            }
        }
        return null;
    }
}
