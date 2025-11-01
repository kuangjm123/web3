package com.wetech.demo.web3j.request;

import java.math.BigInteger;

public class TransferRequest {
    private String to;
    private BigInteger amount;

    // Getters and setters
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }
}
