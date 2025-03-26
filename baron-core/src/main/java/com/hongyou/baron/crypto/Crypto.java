/*
 * Copyright 2024, Hongyou Software Development Studio.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hongyou.baron.crypto;

/**
 * 加密/解密
 *
 * @author Berlin
 */
public interface Crypto {

    /**
     * 生成密匙库
     */
    void generateKeyStore();

    /**
     * 加密
     */
    String encrypt(String plaintext);

    /**
     * 解密
     */
    String decrypt(String ciphertext);

    /**
     * ECB解密，用于前端Crypto库进行加密的数据解密
     */
    String ecbDecrypt(String key, String ciphertext);
}