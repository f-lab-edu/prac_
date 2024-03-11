package com.example.homework.util;

import lombok.Getter;

/**
 * [공통 코드] API 통신에 대한 '에러 코드'를 Enum 형태로 관리를 한다.
 * Global Error CodeList : 전역으로 발생하는 에러코드를 관리한다.
 * Custom Error CodeList : 업무 페이지에서 발생하는 에러코드를 관리한다
 * Error Code Constructor : 에러코드를 직접적으로 사용하기 위한 생성자를 구성한다.
 */
@Getter
public enum ErrorCode {

    /**
     * ******************************* Global Error CodeList ***************************************
     * HTTP Status Code
     * 400 : Bad Request
     * 401 : Unauthorized
     * 403 : Forbidden
     * 404 : Not Found
     * 500 : Internal Server Error
     * *********************************************************************************************
     */

    // 잘못된 서버 요청
    BAD_REQUEST_ERROR(400, "E001", "Bad Request Exception"),

    // @RequestBody 데이터 미 존재
    REQUEST_BODY_MISSING_ERROR(400, "E002", "Required request body is missing"),

    // 유효하지 않은 타입
    INVALID_TYPE_VALUE(400, "E003", " Invalid Type Value"),

    // Request Parameter 로 데이터가 전달되지 않을 경우
    MISSING_REQUEST_PARAMETER_ERROR(400, "E004", "Missing Servlet RequestParameter Exception"),

    // 입력/출력 값이 유효하지 않음
    IO_ERROR(400, "E005", "I/O Exception"),

    // com.google.gson JSON 파싱 실패
    JSON_PARSE_ERROR(400, "E006", "JsonParseException"),

    // com.fasterxml.jackson.core Processing Error
    JACKSON_PROCESS_ERROR(400, "E007", "com.fasterxml.jackson.core Exception"),

    // 권한이 없음
    FORBIDDEN_ERROR(403, "E008", "Forbidden Exception"),

    // 서버로 요청한 리소스가 존재하지 않음
    NOT_FOUND_ERROR(404, "E009", "Not Found Exception"),

    // NULL Point Exception 발생
    NULL_POINT_ERROR(404, "E010", "Null Point Exception"),

    // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
    NOT_VALID_ERROR(404, "E011", "handle Validation Exception"),

    // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
    NOT_VALID_HEADER_ERROR(404, "E012", "Header에 데이터가 존재하지 않는 경우 "),

    // 서버가 처리 할 방법을 모르는 경우 발생
    INTERNAL_SERVER_ERROR(500, "E999", "Internal Server Error Exception"),
    //
    BUSINESS_EXCEPTION_ERROR(200, "B999", "Business Exception Error"),

    /**
     * ******************************* Auth Error CodeList ***************************************
     */

    // 토큰이 유효하지 않음
    TOKEN_EXPIRED(401 , "A401" ,"Token Expired" ),
    // JWT 허용된 토큰이 아님
    TOKEN_SIGNATURE_EXCEPTION_LOGIN(401 , "A402" ,"Token Signature Exception Login" ),
    // JWT 토큰내에서 오류 발생 시
    TOKEN_PARSING_JWT_EXCEPTION(401 , "A403" ,"Token Parsing Jwt Exception" ),
    // 이외 JTW 토큰내에서 오류 발생
    OTHER_TOKEN_ERROR(401 , "A404" ,"Other Token Error" ),

    AUTH_IS_NULL(401, "A405", "Auth Is Null"),
    AUTH_TOKEN_FAIL(401, "A406", "Auth Token Fail"),
    AUTH_TOKEN_INVALID(401, "A407", "Auth Token Invalid"),
    AUTH_TOKEN_NOT_MATCH(401, "A408", "Auth Token Not Match"),
    AUTH_TOKEN_IS_NULL(401, "A409", "Auth Token Is Null"),
    NOT_VALID_AUTHORITIES(401, "A410" , "Invalid Authorities"),

    /**
     * ******************************* JOIN / LOGIN Error CodeList ***************************************
     */
    EMAIL_ALREADY_EXIST(404, "J401" , "Your Mail already Exist"),

    NOT_FOUND_USER(404, "L401" , "Not Found User"),

    NOT_VALID_PASSWORD(404, "L402" , "Not Valid Password"),


    /**
     * ******************************* Custom Error CodeList ***************************************
     */
    // Transaction Insert Error
    SELECT_ERROR(200, "9999", "select Transaction Error Exception"),
    INSERT_ERROR(200, "9999", "Insert Transaction Error Exception"),

    // Transaction Update Error
    UPDATE_ERROR(200, "9999", "Update Transaction Error Exception"),

    // Transaction Delete Error
    DELETE_ERROR(200, "9999", "Delete Transaction Error Exception"),

    ; // End

    /**
     * ******************************* Error Code Constructor ***************************************
     */
    // 에러 코드의 '코드 상태'을 반환한다.
    private final int status;

    // 에러 코드의 '코드간 구분 값'을 반환한다.
    private final String divisionCode;

    // 에러 코드의 '코드 메시지'을 반환한다.
    private final String message;

    // 생성자 구성
    ErrorCode(final int status, final String divisionCode, final String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }
}
