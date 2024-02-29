package com.example.training.service.auth;

import static java.util.stream.Collectors.*;

import com.example.training.model.domain.AccountDetails;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;

    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String getTokenValue(AccountDetails details, int amountToAdd) {
        Instant now = Instant.now();
        String scope = details.getAuthorities().stream()
                              .map(GrantedAuthority::getAuthority)
                              .collect(joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                                         .issuer("self")
                                         .issuedAt(now)
                                         .expiresAt(now.plus(amountToAdd, ChronoUnit.MINUTES))
                                         .subject(details.getUsername())
                                         .claim("scope", scope)
                                         .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String parseToken(String token) {
        try {
            SignedJWT decodedJWT = SignedJWT.parse(token);
            return decodedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
