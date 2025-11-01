package com.wetech.demo.web3j.service;

import com.wetech.demo.web3j.contracts.kjmtoken.KJMToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.crypto.Credentials;
import org.web3j.tx.gas.DefaultGasProvider; // 替换 ContractGasProvider
import java.math.BigInteger;

@Service
public class KJMTokenService {

    private final KJMToken kjmToken;

    // 构造函数：使用 DefaultGasProvider 替代 ContractGasProvider
    public KJMTokenService(Web3j web3j, Credentials credentials,
                           @Value("0xF42c0976C172DbA4181Ef38d4b8F308a12d7d062") String contractAddress) {
        // 初始化合约实例（使用默认 Gas 策略）
        this.kjmToken = KJMToken.load(
                contractAddress,
                web3j,
                credentials,
                new DefaultGasProvider() // 替换原 ContractGasProvider
        );
    }

    // 以下方法实现不变（mint/transfer/balanceOf 等）
    public String mint(String to, BigInteger amount) throws Exception {
        TransactionReceipt receipt = kjmToken.mint(to, amount).send();
        return receipt.getTransactionHash();
    }

    public String transfer(String to, BigInteger amount) throws Exception {
        TransactionReceipt receipt = kjmToken.transfer(to, amount).send();
        return receipt.getTransactionHash();
    }

    public BigInteger balanceOf(String owner) throws Exception {
        return kjmToken.balanceOf(owner).send();
    }

    public String approve(String spender, BigInteger amount) throws Exception {
        TransactionReceipt receipt = kjmToken.approve(spender, amount).send();
        return receipt.getTransactionHash();
    }

    public String transferFrom(String from, String to, BigInteger amount) throws Exception {
        TransactionReceipt receipt = kjmToken.transferFrom(from, to, amount).send();
        return receipt.getTransactionHash();
    }
}