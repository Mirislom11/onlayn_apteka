package com.company.util;

import com.company.dto.profile.ProfileJwtDTO;
import com.company.enums.ProfileRole;
import com.company.exception.ForbiddenException;
import io.jsonwebtoken.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {
    private static final String secretKey = "secretKey";
    private static long expiredTime = 36_000_000;
    public static String createJwt(int  id, ProfileRole role) {
    String jwt =    Jwts
                        .builder()
                        .setSubject(String.valueOf(id))
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .setIssuer("apteka_uz")
                        .claim("role", role.name())
                        .compact();
    return jwt;
    }
    public static String createJwt(int  id) {
        String jwt =    Jwts
                .builder()
                .setSubject(String.valueOf(id))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setIssuer("apteka_uz")
                .compact();
        return jwt;
    }
    @Contract("_ -> new")
    public static @NotNull ProfileJwtDTO decodeJwt(String jwt) {
        JwtParser jwtParser =Jwts.parser();
        jwtParser.setSigningKey(secretKey);
        Jws jws =jwtParser.parseClaimsJws(jwt);
        Claims claims =(Claims) jws.getBody();
        String id =claims.getSubject();
        ProfileRole profileRole = ProfileRole.valueOf((String) claims.get("role"));
        return new ProfileJwtDTO(Integer.parseInt(id), profileRole);
    }
    public static @NotNull Integer getIdForRegistration(String jwt) {
        JwtParser jwtParser =Jwts.parser();
        jwtParser.setSigningKey(secretKey);
        Jws jws =jwtParser.parseClaimsJws(jwt);
        Claims claims =(Claims) jws.getBody();
        String id =claims.getSubject();
        return Integer.parseInt(id);
    }
    public static ProfileJwtDTO getProfile (HttpServletRequest request, ProfileRole role) {
        ProfileJwtDTO jwtDTO = (ProfileJwtDTO) request.getAttribute("jwtDTO");
        if (jwtDTO == null) {
            throw new ForbiddenException("Your jwt Null");
        }
        if (!role.equals(jwtDTO.getRole())) {
            throw new ForbiddenException("You are not allowed to use this feature");
        }
        return jwtDTO;
    }
    public static ProfileJwtDTO getProfile (HttpServletRequest request, ProfileRole roleOne, ProfileRole roleTwo) {
        ProfileJwtDTO jwtDTO = (ProfileJwtDTO) request.getAttribute("jwtDTO");
        if (jwtDTO == null) {
            throw new ForbiddenException("Your jwt Null");
        }
        if (!roleOne.equals(jwtDTO.getRole()) && !roleTwo.equals(jwtDTO.getRole())) {
            throw new ForbiddenException("You are not allowed to use this feature");
        }
        return jwtDTO;
    }
    public static ProfileJwtDTO getProfile (HttpServletRequest request, ProfileRole roleOne, ProfileRole roleTwo, ProfileRole roleThree) {
        ProfileJwtDTO jwtDTO = (ProfileJwtDTO) request.getAttribute("jwtDTO");
        if (jwtDTO == null) {
            throw new ForbiddenException("Your jwt Null");
        }
        if (!roleOne.equals(jwtDTO.getRole()) && !roleTwo.equals(jwtDTO.getRole()) && !roleThree.equals(jwtDTO.getRole())) {
            throw new ForbiddenException("You are not allowed to use this feature");
        }
        return jwtDTO;
    }
    public static ProfileJwtDTO getProfile (HttpServletRequest request) {
        ProfileJwtDTO jwtDTO = (ProfileJwtDTO) request.getAttribute("jwtDTO");
        if (jwtDTO == null) {
            throw new ForbiddenException("Your jwt Null");
        }
        return jwtDTO;
    }

}
