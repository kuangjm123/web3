package com.wetech.demo.web3j.request;

import java.math.BigInteger;

public class ApproveRequest {
    private String spender;
    private BigInteger amount;

    // Getters and setters
    public String getSpender() {
        return spender;
    }

    public void setSpender(String spender) {
        this.spender = spender;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }
}
