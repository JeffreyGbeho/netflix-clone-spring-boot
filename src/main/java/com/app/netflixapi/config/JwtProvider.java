package com.app.netflixapi.config;

import com.app.netflixapi.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private String secretKey = "mySecretKey";

    public String createToken(User user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(user.getEmail())
                .withIssuedAt(now)
                .withExpiresAt(validity)
//                .withClaim("firstName", user.getFirstName())
//                .withClaim("lastName", user.getLastName())
                .sign(algorithm);
    }

    public String validateToken(String token) {
        JWTVerifier verifier = JWT
                .require(Algorithm.HMAC256(secretKey))
                .acceptLeeway(1)
                .acceptExpiresAt(5)
                .build();

        DecodedJWT jwt = verifier.verify(token);
        String email = jwt.getSubject();
        return email;
    }
}
