package com.wetech.demo.web3j.request;

import lombok.Data;
import java.util.Map;

/**
 * 接口统一响应结果
 */
@Data
public class ApiResponse {
    private int code;               // 状态码：200成功，400失败
    private String message;         // 响应信息
    private Map<String, Object> data; // 响应数据（交易哈希、余额等）

    // 成功响应构造
    public static ApiResponse success(String message, Map<String, Object> data) {
        ApiResponse response = new ApiResponse();
        response.setCode(200);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    // 失败响应构造
    public static ApiResponse fail(String message) {
        ApiResponse response = new ApiResponse();
        response.setCode(400);
        response.setMessage(message);
        response.setData(null);
        return response;
    }
}