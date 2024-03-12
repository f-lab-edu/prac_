package com.example.homework.util;

import com.example.homework.config.exception.BusinessExceptionHandler;
import com.example.homework.dto.UserDto;
import com.example.homework.model.codes.ErrorCode;
import com.example.homework.service.UserService;
import com.example.homework.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static UserService userService;

    private static UserDetailsServiceImpl userDetailsService;

    private static Key key;

    @Getter
    @Value(value = "${jwt.secret-key}")
    private String jwtSecretKey;

    @PostConstruct
    public void init() {
        String base64EncodedSecretKey = encodeBase64SecretKey(this.jwtSecretKey);
        key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
    }

    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 사용자 정보를 기반으로 토큰을 생성하여 반환 해주는 메서드
     *
     * @param  userDto : 사용자 정보
     * @return String : 토큰
     */
    public static String generateJwtToken(UserDto userDto) {
        // 사용자 시퀀스를 기준으로 JWT 토큰을 발급하여 반환해줍니다.
        JwtBuilder builder = Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(userDto))
                .setSubject(String.valueOf(userDto.getEmail()))
                .setExpiration(createExpiredDate())
                .setIssuedAt(Calendar.getInstance().getTime())
                .signWith(key,SignatureAlgorithm.HS256);
        return builder.compact();
    }

    /**
     * Header 내에 토큰을 추출합니다.
     *
     * @param header 헤더
     * @return String
     */
    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }


    /**
     * 토큰의 만료기간을 지정하는 함수
     *
     * @return Calendar
     */
    private static Date createExpiredDate() {
        // 토큰 만료시간은 30일으로 설정
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, 8);     // 8시간
        // c.add(Calendar.DATE, 1);         // 1일
        return c.getTime();
    }

    /**
     * JWT의 "헤더" 값을 생성해주는 메서드
     *
     * @return HashMap<String, Object>
     */
    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    /**
     * 사용자 정보를 기반으로 클래임을 생성해주는 메서드
     *
     * @param userDto 사용자 정보
     * @return Map<String, Object>
     */
    private static Map<String, Object> createClaims(UserDto userDto) {
        Map<String, Object> claims = new HashMap<>();

        log.info("userId :" + userDto.getUserId() );
        log.info("userEmail :" + userDto.getEmail());
        log.info("authorities : " + userDto.getAuthorityList());

        claims.put("userId", userDto.getUserId());
        claims.put("email", userDto.getEmail());
        claims.put("authorities", userDto.getAuthorityList());
        return claims;
    }

    /**
     * 유효한 토큰인지 확인 해주는 메서드
     *
     * @param token String  : 토큰
     * @return boolean      : 유효한지 여부 반환
     */
    public static boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);

            log.info("expireTime :" + claims.getExpiration());
            log.info("userPk :" + claims.get("userPk") );
            log.info("userEmail :" + claims.get("userEmail"));
            log.info("userNm :" + claims.get("userNm"));

            return true;
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            return false;
        } catch (JwtException exception) {
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.error("Token is null");
            return false;
        }
    }

    /**
     * 토큰 정보를 기반으로 Claims 정보를 반환받는 메서드
     *
     * @param token : 토큰
     * @return Claims : Claims
     */
    private static Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 토큰을 기반으로 사용자 정보를 반환받는 메서드
     *
     * @param token : 토큰
     * @return String : 사용자 아이디 (이메일)
     */
    public static String getUserEmailFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("email").toString();
    }

    /**
     *  토큰을 복호화하여 Authentication 객체를 만들어 반환
     *
     * @param token : 토큰
     * @return String : 사용자 정보
     */
    public static Authentication getAuthentication(String token) {
        // 토큰 복호화
        Claims claims = getClaimsFromToken(token);

        if (claims.get("authorities") == null) {
            throw new RuntimeException(ErrorCode.NOT_VALID_AUTHORITIES.getMessage());
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("authorities").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.get("email").toString());
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    /**
     * SecurityContextHolder 에 저장된 사용자 정보를 반환한다.
     * @return Long : 사용자 pk
     */
    public static UserDto getUserFromAuthentication() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = UserDto.builder()
                .email(userDetails.getUsername())
                .build();

        return userService.findUserByEmail(userDto).orElseThrow(
                () -> new BusinessExceptionHandler(ErrorCode.NOT_FOUND_USER.getMessage() , ErrorCode.NOT_FOUND_USER));
    }
}
