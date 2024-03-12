package com.example.homework.config.handler;

import com.example.homework.dto.UserDetailsDto;
import com.example.homework.dto.UserDto;
import com.example.homework.util.AuthConstants;
import com.example.homework.util.ConvertUtil;
import com.example.homework.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * 사용자의 ‘인증’에 대해 성공하였을 경우 수행되는 Handler로 성공에 대한 사용자에게 반환값을 구성하여 전달합니다
 */
@Slf4j
@Configuration
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.debug("3.CustomLoginSuccessHandler");

        UserDto userDto = ((UserDetailsDto) authentication.getPrincipal()).getUserDto();
        JSONObject userInfo = (JSONObject) ConvertUtil.convertObjectToJsonObject(userDto);

        HashMap<String, Object> responseMap = new HashMap<>();
        JSONObject jsonObject;

        responseMap.put("userInfo", userInfo);
        responseMap.put("resultCode", 200);
        responseMap.put("failMsg", null);;

        jsonObject = new JSONObject(responseMap);

        response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.BEARER_PREFIX + JwtTokenProvider.generateJwtToken(userDto));
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jsonObject);
        printWriter.flush();
        printWriter.close();


    }
}