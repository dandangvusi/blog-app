package com.dan.blogapp.security;

import com.dan.blogapp.exception.BlogAPIException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpiInMs;

    // generate token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currDate = new Date();
        Date expiDate = new Date(currDate.getTime() + this.jwtExpiInMs);
        String token = Jwts.builder().setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        return token;
    }

    //get username from token
    public String getUsernameByJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(this.jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // validate jwt token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException exc){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid jwt signature");
        }catch (MalformedJwtException exc){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid jwt token");
        }catch (ExpiredJwtException exc){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired jwt token");
        }catch (UnsupportedJwtException exc){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported jwt token");
        }catch (IllegalArgumentException exc){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "jwt claims string is empty");
        }
    }
}
