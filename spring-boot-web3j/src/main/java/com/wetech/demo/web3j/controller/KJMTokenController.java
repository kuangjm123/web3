package com.wetech.demo.web3j.controller;

import com.wetech.demo.web3j.service.KJMTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/kjm-token")
public class KJMTokenController {

    private final KJMTokenService kjmTokenService;

    // 注入 Service 依赖
    public KJMTokenController(KJMTokenService kjmTokenService) {
        this.kjmTokenService = kjmTokenService;
    }

    /**
     * 铸造代币接口
     * @param to 接收地址
     * @param amount 铸造数量
     */
    @PostMapping("/mint")
    public ResponseEntity<Map<String, Object>> mint(
            @RequestParam String to,
            @RequestParam BigInteger amount) {
        return handleContractCall(() -> {
            String txHash = kjmTokenService.mint(to, amount);
            return buildSuccessResponse("铸造成功", txHash);
        });
    }

    /**
     * 转账代币接口
     * @param to 接收地址
     * @param amount 转账数量
     */
    @PostMapping("/transfer")
    public ResponseEntity<Map<String, Object>> transfer(
            @RequestParam String to,
            @RequestParam BigInteger amount) {
        return handleContractCall(() -> {
            String txHash = kjmTokenService.transfer(to, amount);
            return buildSuccessResponse("转账成功", txHash);
        });
    }

    /**
     * 查询余额接口
     * @param owner 地址
     */
    @GetMapping("/balance")
    public ResponseEntity<Map<String, Object>> balanceOf(@RequestParam String owner) {
        return handleContractCall(() -> {
            BigInteger balance = kjmTokenService.balanceOf(owner);
            Map<String, Object> data = new HashMap<>();
            data.put("owner", owner);
            data.put("balance", balance);
            return buildSuccessResponse("查询成功", data);
        });
    }

    /**
     * 授权接口
     * @param spender 授权地址
     * @param amount 授权数量
     */
    @PostMapping("/approve")
    public ResponseEntity<Map<String, Object>> approve(
            @RequestParam String spender,
            @RequestParam BigInteger amount) {
        return handleContractCall(() -> {
            String txHash = kjmTokenService.approve(spender, amount);
            return buildSuccessResponse("授权成功", txHash);
        });
    }

    /**
     * 授权转账接口
     * @param from 授权地址
     * @param to 接收地址
     * @param amount 转账数量
     */
    @PostMapping("/transfer-from")
    public ResponseEntity<Map<String, Object>> transferFrom(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam BigInteger amount) {
        return handleContractCall(() -> {
            String txHash = kjmTokenService.transferFrom(from, to, amount);
            return buildSuccessResponse("授权转账成功", txHash);
        });
    }

    // 统一异常处理和响应构建
    private interface ContractOperation<T> {
        T execute() throws Exception;
    }

    private <T> ResponseEntity<Map<String, Object>> handleContractCall(ContractOperation<T> operation) {
        try {
            return (ResponseEntity<Map<String, Object>>) ResponseEntity.ok(operation.execute());
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "操作失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    private Map<String, Object> buildSuccessResponse(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        response.put("data", data);
        return response;
    }
}