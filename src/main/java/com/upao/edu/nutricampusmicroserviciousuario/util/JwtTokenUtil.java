package com.upao.edu.nutricampusmicroserviciousuario.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final long serialVersionUID = 7383112237L;
    public static final int JWT_TOKEN_VALIDITY = 10*60;         // 10 minutes

    @Value("${jwt.secret}")
    public String secret;

    //generate token for required data i.e. user details
    public String generateToken(UserDetails userDetails){
        // we can set extra info this claims hashmap and below defined getCustomParamFromToken to get it by passing Map key.
        Map<String, Object> claims = new HashMap<>();
//        claims.put("sub-application", "inventory");
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setAudience("rajblowplast").setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +  JWT_TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String userName = getUserNameFromToken(token);
        return (!isTokenExpired(token) && userName.equals(userDetails.getUsername()));
    }

    // Obtener nombre del Token (Para Controlador)
    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Obtener el audience del Token (Para controlador de ser necesario)
    public String getAudienceFromToken(String token) {
        return getClaimFromToken(token, Claims::getAudience);
    }

    // Obtener la fecha de expiración (Para controlador de ser necesario)
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // Comprueba si el token expiró
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Obtener parametros customizados del Token (Para controlador de ser necesario)
    public Object getCustomParamFromToken(String token, String param){
        final Claims claims = getAllClaimsFromToken(token);
        logger.debug("Requested param from token: {}", param);
        return claims.get(param);
    }

    // Obtener todos los parametros del Token (Para controlador de ser necesario)
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Obtener todos los parametros del Token (Para controlador de ser necesario)
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
