package com.example.homework.controller;

import com.example.homework.dto.UserRequestDto;
import com.example.homework.service.UserService;
import com.example.homework.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * @desc 유저 정보 저장
     * @param requestDto UserRequestDto.JoinUser
     * @return ApiResponseWrapper<ApiResponse> : 응답 결과 및 응답 코드 반환
     */
    @PostMapping(value = "/join" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> saveUser(@RequestBody UserRequestDto.JoinUser requestDto) {
        return new ResponseEntity<>(userService.saveUser(requestDto), HttpStatus.OK);
    }

}
