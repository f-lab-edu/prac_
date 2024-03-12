package com.example.homework.config.interceptor;

import com.example.homework.config.exception.BusinessExceptionHandler;
import com.example.homework.model.codes.ErrorCode;
import com.example.homework.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        String header = request.getHeader("Authorization");

        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            log.debug("if request options method is options, return true");

            return true;
        }

        if (header != null) {

            String token = jwtTokenProvider.getTokenFromHeader(header);

            if (jwtTokenProvider.isValidToken(token)) {
                String userId = jwtTokenProvider.getUserEmailFromToken(token);
                if (userId == null) {
                    log.debug("token isn't userId(Email)");
                    throw new BusinessExceptionHandler("token isn't userId(Email)", ErrorCode.AUTH_TOKEN_NOT_MATCH);
                }
                return true;
            } else {
                throw new BusinessExceptionHandler("token is invalid", ErrorCode.AUTH_TOKEN_INVALID);
            }
        } else {
            throw new BusinessExceptionHandler("Header not exist token", ErrorCode.AUTH_TOKEN_IS_NULL);
        }
    }
}