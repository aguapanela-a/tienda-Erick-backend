package com.acm.tiendaerick.compartido.seguridad.config;
import com.acm.tiendaerick.compartido.seguridad.filtro.FiltroJwt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private final FiltroJwt filtroJwt;

    public CorsConfig(FiltroJwt filtroJwt) {
        this.filtroJwt = filtroJwt;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        String frontendUrl = System.getenv("FRONTEND_URL");

        registry.addMapping("/**") // Aplica a todos los endpoints
                .allowedOriginPatterns(frontendUrl != null ? frontendUrl : "http://localhost:5173") // Permite el front dela tienda
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }

    // Bean que decide quién puede acceder a qué endpoints y quién se encarga de la autenticación (filtro jwt)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        return http
                .cors(cors->cors.configurationSource(corsConfigurationSource)) // Configura CORS con el bean CorsConfigurationSource
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF (no es necesario para APIs REST)

                //inicializamos con los filtros de seguridad de spring para cada endpoint
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Permite acceso sin autenticación a los endpoints de autenticación
                        .anyRequest().authenticated() // Requiere autenticación para cualquier otro endpoint

                        //NOTE: dado que esta API no usa roles ni permisos específicos, no es necesario configurar reglas más detalladas. Si en el futuro se agregan roles o permisos, aquí es donde se definirían las reglas de acceso para cada endpoint.
                )
                .addFilterBefore(filtroJwt, UsernamePasswordAuthenticationFilter.class) // Agrega el filtro JWT antes del filtro de autenticación de Spring
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(
                                (req, res, e) ->
                                {
                                    res.setStatus(401);
                                    res.setContentType("application/json");
                                    res.getWriter().write("{\"estado\":401,\"mensaje\":\"Token inválido o vencido\"}");
                                }
                        ).accessDeniedHandler(
                                (req, res, e) ->
                                {
                                    res.setStatus(403);
                                    res.setContentType("application/json");
                                    res.getWriter().write("{\"estado\":403,\"mensaje\":\"No tienes permisos para acceder a este contenido\"}");
                                }
                        )
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}