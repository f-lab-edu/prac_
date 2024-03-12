package com.example.homework.service;

import com.example.homework.dto.UserDto;
import com.example.homework.dto.UserRequestDto;
import com.example.homework.model.response.ApiResponse;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> findUserByEmail(UserDto userDto);

    ApiResponse saveUser(UserRequestDto.JoinUser requestDto);
}
