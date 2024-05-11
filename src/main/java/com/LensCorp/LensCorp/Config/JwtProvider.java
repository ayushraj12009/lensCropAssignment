package com.LensCorp.LensCorp.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class JwtProvider  {

    // Secret key used for signing and parsing JWT tokens
    private static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    // Method to generate JWT token based on user authentication
    public static String generateToken (Authentication auth){
        String jwt = Jwts.builder()
                .setIssuer("XindusTrader").setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email", auth.getName())
                .signWith(key)
                .compact();

        return jwt;
    }

    // Method to extract email from JWT token
    public static String getEamilFromJwtToken(String jwt){
        jwt = jwt.substring(7);

        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        String email = String.valueOf(claims.get("email"));

        return email;

    }


//    public static void invalidateToken(String token) {
//        // Create a list to store invalidated tokens
//        List<String> invalidatedTokens = new ArrayList<>();
//
//        // Check if the token is already in the list of invalidated tokens
//        if (!invalidatedTokens.contains(token)) {
//            // Add the token to the list of invalidated tokens
//            invalidatedTokens.add(token);
//
//            // Store the list of invalidated tokens in a database or a file
//            // For simplicity, let's store it in memory for this example
//            invalidatedTokensList = invalidatedTokens;
//        }
//    }

}