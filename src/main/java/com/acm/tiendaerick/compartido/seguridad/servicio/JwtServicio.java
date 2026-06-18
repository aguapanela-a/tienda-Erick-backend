package com.acm.tiendaerick.compartido.seguridad.servicio;

import com.acm.tiendaerick.compartido.usuarios.entidad.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public class JwtServicio {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiracion;


    //metodo para genrar el token a partir de un usuario, el token se genera con los datos del usuario (Claims) y se firma con la llave secreta (Secret Key) con un algoritmo de firma (HS256)
    public String generateToken(User usuario) throws JOSEException {

        //Primero crear un JWSSigner que es un objeto que se encarga de firmar el JWT con la llave secreta
        JWSSigner signer = new MACSigner(secretKey.getBytes());

        //Segundo crear un JWTClaimsSet que es un objeto que contiene los datos del JWT (Claims)
        // en este caso los datos son el username, id, nombre y numero de telefono del usuario, y la fecha de expiracion del token
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(usuario.getUsername())
                .claim("id", usuario.getId().toString())
                .claim("nombre", usuario.getNombre())
                .claim("numeroTelefono", usuario.getNumeroTelefono())
                .expirationTime(new Date(System.currentTimeMillis() + expiracion))
                .build();

        //Luego crear un SignedJWT que es un objeto que contiene el JWT (Datos del usuario) y firma el JWT con el JWSSigner creado anteriormente (Secret Key) con un algoritmo de firma (HS256)
        SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        jwt.sign(signer);

        return jwt.serialize();
    }

    //metodo para validar el tokem
    public String extraerTelefono(String token) throws JOSEException, ParseException {
        //parsea el token para obtener el numero de telefono
        SignedJWT jwt = SignedJWT.parse(token);

        //Creamos un objeto verificador JWSVerifier con la llave secreta
        JWSVerifier verifier = new MACVerifier(secretKey.getBytes());

        //validar que el token sea válido
        if(!jwt.verify(verifier)){
            throw new SecurityException("Token inválido");
        }

        //validar fecha de expiración
        Date expiracion = jwt.getJWTClaimsSet().getExpirationTime();
        if(expiracion.before(new Date())){
            throw new SecurityException("Token expirado");
        }

        //retornar el numero de telefono del usuario que se encuentra en los claims del token
        return jwt.getJWTClaimsSet().getStringClaim("numeroTelefono");
    }
}
