/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.crypto;

/**
 * 加密/解密
 *
 * @author Hong Bo Lin
 */
public interface Crypto {

    /**
     * 生成密匙库
     */
    void generateKeyStore();

    /**
     * 加密
     */
    String encrypt(final String plaintext);

    /**
     * 解密
     */
    String decrypt(final String ciphertext);

    /**
     * ECB解密，用于前端Crypto库进行加密的数据解密
     */
    String ecbDecrypt(final String key, final String ciphertext);
}