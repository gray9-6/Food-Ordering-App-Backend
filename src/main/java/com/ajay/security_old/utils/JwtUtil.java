//package com.ajay.security.utils;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Component
//public class JwtUtil {
//
//    // to validate to authentication and authorization we need secret key
//    private final String SECRET_KEY ="a24b42d234b8f3844769474ed3aa19f246d3e45538f5d440a4bd75e5b255e40b";
//    private final long HOURS=24*60*60*1000;   // expiration hours
//
//    public String generateToken(String userName){
//        Map<String,Object> claims = new HashMap<>();
//        return createToken(claims,userName);
//    }
//
//    private String createToken(Map<String,Object> claims,String userName){
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(userName)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + HOURS))
//                .signWith(getSignKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    private Key getSignKey(){
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    /*========================================================================*/
//    public <T> T extractClaim(String token, Function<Claims,T> resolver){
//        Claims claims = extractAllClaims(token);
//        return resolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token){
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSignKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    /*========================================================================*/
//
//    public String extractUserName(String token){
//        return extractClaim(token,Claims::getSubject);
//    }
//
//    public Boolean validateToken(String token, UserDetails userDetails){
//        String userName =extractUserName(token);
//        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
//    private Boolean isTokenExpired(String token){
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token){
//        return extractClaim(token,Claims::getExpiration);
//    }
//
//}
