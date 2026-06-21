package com.acm.tiendaerick.compartido.seguridad.filtro;

import com.acm.tiendaerick.excepciones.ExcepcionesTienda;
import com.acm.tiendaerick.compartido.seguridad.servicio.JwtServicio;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Component
public class FiltroJwt extends OncePerRequestFilter {

    private final JwtServicio jwtServicio;

    public FiltroJwt(JwtServicio jwtServicio) {
        this.jwtServicio = jwtServicio;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // verifica que el encabezado sea Authorization
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // no tiene accessToken, sigue (con el SecurityFilterChain de Spring)
            return;
        }
        //extraer el accessToken del encabezado Authorization
        String token = authHeader.substring(7);

        try{
            //validar y extraer el telefono del accessToken
            String telefono = jwtServicio.extraerTelefono(token);

            //decirle a spring el usuario que está autenticado usando el telefono y una lista vacia de roles porque no necesitamos roles en este proyecto
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(telefono, null, List.of());

            //meter la autenticación al contexto de spring
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }catch (ExcepcionesTienda | JOSEException | ParseException e){
            // Si el accessToken es inválido, no autenticamos y dejamos que Spring maneje la respuesta (probablemente 401)
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Toker inválido - "+ e.getMessage());
            return;
        }

        // Si el try-catch anterior no lanzó error continuará con la autenticación del usuario y la cadena de filtros
        // Continuar con la cadena de filtros (el Bean 'SecurityFilterChain' -> quien decide cuales endpoints se pueden acceder y quien lo hace)
        filterChain.doFilter(request, response);
    }
}
