package com.wetech.demo.web3j.request;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TransferFromRequest {
    private String fromAddress;
    private String toAddress;
    private BigInteger amount;

    // Getter和Setter
    public String getFromAddress() { return fromAddress; }
    public void setFromAddress(String fromAddress) { this.fromAddress = fromAddress; }
    public String getToAddress() { return toAddress; }
    public void setToAddress(String toAddress) { this.toAddress = toAddress; }
    public BigInteger getAmount() { return amount; }
    public void setAmount(BigInteger amount) { this.amount = amount; }
}
