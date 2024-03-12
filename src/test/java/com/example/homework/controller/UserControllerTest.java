package com.example.homework.controller;

import com.example.homework.dto.UserRequestDto;
import com.example.homework.service.UserService;
import com.example.homework.model.response.ApiResponse;
import com.example.homework.model.codes.SuccessCode;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        // 옵션 추가 (인터셉터,필터, 예외 핸들러 등)
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

    @Test
    @DisplayName("유저 정보 저장 Mock 테스트")
    void saveUser() throws Exception {

        // given
        UserRequestDto.JoinUser requestDto = UserRequestDto.JoinUser.builder()
                .email("tester@gmail.com")
                .password("1234")
                .build();

        ApiResponse response = new ApiResponse(null, SuccessCode.INSERT_SUCCESS.getStatus(), SuccessCode.INSERT_SUCCESS.getMessage());

        when(userService.saveUser(any(UserRequestDto.JoinUser.class)))
                .thenReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto)));

        //then
        resultActions.andExpect(status().isOk())
                        .andExpect(jsonPath("resultCode", response.getResultCode()).exists())
                        .andExpect(jsonPath("resultMsg", response.getResultMsg()).exists())
                        .andDo(print());
    }
}