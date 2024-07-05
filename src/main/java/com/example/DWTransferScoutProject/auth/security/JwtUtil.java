package com.example.DWTransferScoutProject.auth.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    private final AccountDetailsServiceImpl userDetailsService;

    public JwtUtil(AccountDetailsServiceImpl userDetailsService) {
        super();
        this.userDetailsService = userDetailsService;
    }

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey.trim());
        key = Keys.hmacShaKeyFor(bytes);
    }
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (bearerToken == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    String name = c.getName();
                    String value = c.getValue();
                    if (name.equals(AUTHORIZATION_HEADER)) {
                        bearerToken = value;
                    }
                }
            }
        }

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            String token = bearerToken.substring(BEARER_PREFIX.length()).trim();
            return token;
        }
        return null;
    }

    public String createToken(String username, ApplicationRoleEnum userType) {
        Date date = new Date();

        String token = BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, userType)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();

        log.info("Generated JWT Token: {}", token); // 추가된 로그
        return token;
    }


    public boolean validateToken(String token) {
        try {
            log.debug("Validating token: {}", token);
            token = token.trim();
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT token compact of handler are invalid: {}", e.getMessage());
        }
        return false;
    }

    public Claims getAccountInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String getAccountTypeFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.get(AUTHORIZATION_KEY, String.class);
    }

    public String getAccountnameFromToken(String token) {
        Claims claims = getAccountInfoFromToken(token);
        return claims.getSubject();
    }
}
