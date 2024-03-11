package com.example.homework.service.impl;

import com.example.homework.config.BusinessExceptionHandler;
import com.example.homework.dto.UserRequestDto;
import com.example.homework.model.User;
import com.example.homework.repository.UserRepository;
import com.example.homework.service.UserService;
import com.example.homework.util.ApiResponse;
import com.example.homework.util.ErrorCode;
import com.example.homework.util.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
        return new ApiResponse(user.getId(), SuccessCode.INSERT_SUCCESS.getStatus(),SuccessCode.INSERT_SUCCESS.getMessage());
    }
}
