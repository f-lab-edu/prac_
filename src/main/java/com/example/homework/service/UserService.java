package com.example.homework.service;

import com.example.homework.dto.UserRequestDto;
import com.example.homework.util.ApiResponse;

public interface UserService {

    ApiResponse saveUser(UserRequestDto.JoinUser requestDto);
}
