package com.wetech.demo.web3j.request;

public class MintRequest {
    private String to;
    private java.math.BigInteger amount;

    // Getters and setters
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public java.math.BigInteger getAmount() {
        return amount;
    }

    public void setAmount(java.math.BigInteger amount) {
        this.amount = amount;
    }
}
