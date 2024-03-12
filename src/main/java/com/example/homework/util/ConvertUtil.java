package com.example.homework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConvertUtil {

    /**
     * [공통함수] Object(VO)형을 JSON Object 형태로 변환 함수
     *
     * @param obj {Object}
     * @return {Object}
     */
    public static Object convertObjectToJsonObject(Object obj) {

        ObjectMapper om = new ObjectMapper();
        JSONParser parser = new JSONParser();
        String convertJsonString = "";
        Object convertObj = new Object();

        // VO ==> JSON(String) 파싱
        try {
            convertJsonString = om.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // JSON(String) => JSON 파싱
        try {
            convertObj = parser.parse(convertJsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertObj;
    }
}
