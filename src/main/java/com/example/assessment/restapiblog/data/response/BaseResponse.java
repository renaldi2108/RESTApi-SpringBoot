package com.example.assessment.restapiblog.data.response;

import java.util.LinkedHashMap;
import java.util.Map;

public class BaseResponse {
    private static BaseResponse instance;

    private BaseResponse() {
    }

    public static BaseResponse createInstance() {
        if (instance == null) {
            synchronized (BaseResponse.class) {
                if (instance == null) {
                    instance = new BaseResponse();
                }
            }
        }
        return instance;
    }

    public Map<String, Object> build(boolean status, String message, Object content, int flag) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", status);
        response.put("message", message);
        if(flag==0){
            response.put("content", content);
        }

        return response;
    }
}