package com.ajay.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {

    private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication){

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = populateAuthorities(authorities);   // converting the roles from granted authority to string

        // preparing the jwt token
        String jwt = Jwts.builder().setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))   // 24 hours
                .claim("email",authentication.getName())
                .claim("authorities",roles)
                .signWith(key)
                .compact();

        return jwt;
    }

    public String getEmailFromJwtToken(String jwt){
        jwt = jwt.substring(7);

        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();  // in claim we have two things , email and role
        String email = String.valueOf(claims.get("email"));
        return email;
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authSet = new HashSet<>();

        for (GrantedAuthority authority :authorities) {
            authSet.add(authority.getAuthority());
        }

        return String.join(",",authSet);
    }
}
