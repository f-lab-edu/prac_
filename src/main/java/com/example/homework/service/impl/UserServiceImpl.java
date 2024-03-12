package com.example.homework.service.impl;

import com.example.homework.config.exception.BusinessExceptionHandler;
import com.example.homework.dto.UserDto;
import com.example.homework.dto.UserRequestDto;
import com.example.homework.model.User;
import com.example.homework.repository.UserRepository;
import com.example.homework.service.UserService;
import com.example.homework.model.response.ApiResponse;
import com.example.homework.model.codes.ErrorCode;
import com.example.homework.util.JwtTokenProvider;
import com.example.homework.model.codes.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * @desc 이메일 (Id) -> 유저 정보 조회
     * @param userDto UserDto
     * @return UserDto
     */
    @Override
    public Optional<UserDto> findUserByEmail(UserDto userDto) {
        return userRepository.findUserByEmail(userDto.getEmail());
    }

    /**
     * @desc 유저 정보 저장
     * @param requestDto UserRequestDto.JoinUser
     * @return ApiResponse
     */
    @Override
    public ApiResponse saveUser(UserRequestDto.JoinUser requestDto) {
        // 이메일 중복 여부 추가
        User user = User.createJoinUser(requestDto);
        User savedUser = userRepository.save(user);

        if (savedUser.getId() == null) {
            throw new BusinessExceptionHandler(ErrorCode.INSERT_ERROR);
        }
        return new ApiResponse(user.getId(), SuccessCode.JOIN_SUCCESS);
    }
}
