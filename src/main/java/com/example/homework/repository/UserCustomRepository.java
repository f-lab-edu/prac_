package com.example.homework.repository;

import com.example.homework.dto.UserDto;

import java.util.Optional;

public interface UserCustomRepository {

    Optional<UserDto> findUserByEmail(String email);
}
