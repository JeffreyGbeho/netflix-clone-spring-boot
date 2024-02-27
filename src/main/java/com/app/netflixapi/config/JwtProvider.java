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
//    @Value("${security.jwt.token.secret-key:secret-key}")
//    private String secretKey;
//
//    private final UserService userService;
//
//    @PostConstruct
//    protected void init() {
//        // this is to avoid having the raw secret key available in the JVM
//        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
//    }

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

//    public Authentication validateToken(String token) {
//        Algorithm algorithm = Algorithm.HMAC256(secretKey);
//
//        JWTVerifier verifier = JWT.require(algorithm)
//                .build();
//
//        DecodedJWT decoded = verifier.verify(token);
//
//        UserDto user = UserDto.builder()
//                .login(decoded.getSubject())
//                .firstName(decoded.getClaim("firstName").asString())
//                .lastName(decoded.getClaim("lastName").asString())
//                .build();
//
//        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
//    }
//
//    public Authentication validateTokenStrongly(String token) {
//        Algorithm algorithm = Algorithm.HMAC256(secretKey);
//
//        JWTVerifier verifier = JWT.require(algorithm)
//                .build();
//
//        DecodedJWT decoded = verifier.verify(token);
//
//        UserDto user = userService.findByLogin(decoded.getSubject());
//
//        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
//    }

}
