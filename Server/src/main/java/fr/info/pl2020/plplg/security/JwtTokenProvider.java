package fr.info.pl2020.plplg.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtTokenProvider {

    @Value("${jwt.expirationInMs}")
    private long jwtExpirationInMs;

    private static String secret = new SecureRandom().ints(16, '!', '{').mapToObj(i -> String.valueOf((char) i)).collect(Collectors.joining());

    public String generateToken(Authentication authentication) {
        StudentDetails studentDetails = (StudentDetails) authentication.getPrincipal();
        return this.generateToken(studentDetails);
    }

    public String generateToken(StudentDetails studentDetails) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(Integer.toString(studentDetails.getId()))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (RuntimeException ignored) {
            return false;
        }
    }

    public int getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Integer.parseInt(claims.getSubject());
    }

}