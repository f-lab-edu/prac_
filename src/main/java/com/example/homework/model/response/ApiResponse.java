package com.example.homework.model.response;

import com.example.homework.model.codes.SuccessCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponse {

    private Object result;

    private int resultCode;

    private String resultMsg;

    @Builder
    public ApiResponse(final Object result, final int resultCode, final String resultMsg) {
        this.result = result;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Builder
    public ApiResponse(final Object result, SuccessCode successCode) {
        this.result = result;
        this.resultCode = successCode.getStatus();
        this.resultMsg = successCode.getMessage();
    }

}
