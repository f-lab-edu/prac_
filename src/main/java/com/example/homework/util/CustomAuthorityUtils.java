package com.example.homework.util;

import com.example.homework.config.exception.BusinessExceptionHandler;
import com.example.homework.model.codes.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthorityUtils {

    /**
     * 권한 정보 셋팅
     * @param authorities : 유저 권한 목록
     * @return List<GrantedAuthority> : userDetails 유저 권한
     */
    public static List<GrantedAuthority> createAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * 권한 정보 유효성 검사
     * @param role String
     */
    public static void verifiedRole(String role) {
        if (role == null) {
            throw new BusinessExceptionHandler(ErrorCode.NOT_VALID_AUTHORITIES);
        } else if (!role.equals(AuthConstants.ROLE_USER) && !role.equals(AuthConstants.ROLE_ADMIN)) {
            throw new BusinessExceptionHandler(ErrorCode.NOT_VALID_AUTHORITIES);
        }
    }
}