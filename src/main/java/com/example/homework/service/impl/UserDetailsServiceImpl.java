package com.example.homework.service.impl;

import com.example.homework.dto.UserDetailsDto;
import com.example.homework.dto.UserDto;
import com.example.homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userEmail) {
        UserDto userDto = UserDto
                .builder()
                .email(userEmail)
                .build();

        return userService.findUserByEmail(userDto)
                .map(UserDetailsDto::new)
                .orElseThrow(() -> new AuthenticationServiceException("Authentication failed : " + userEmail));
    }
}
