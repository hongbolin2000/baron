/*
 * Copyright 2024, Hongyou Software Development Studio.
 */
package com.hongyou.baron.crypto;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.hongyou.baron.logging.Log;
import com.hongyou.baron.logging.LogFactory;
import com.hongyou.baron.util.CharsetUtil;
import com.hongyou.baron.util.ResourceUtil;
import com.hongyou.baron.util.StringUtil;
import lombok.Getter;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Base64;

/**
 * AES
 *
 * @author Berlin
 */
public class AesManager implements Crypto {

    /**
     * 私有构造函数
     */
    private AesManager() {}

    /**
     * logger
     */
    private static final Log logger = LogFactory.getLog(AesManager.class);

    /**
     * 输出文件夹
     */
    private static final String RESOURCES = "/src/main/resources/";

    /**
     * 输出文件名
     */
    private static final String KEY_STORE = "aes.jks";

    /**
     * 密匙别名
     */
    private static final String ALIAS = "aes-key";

    /**
     * 密匙
     */
    private String aesKey;

    /**
     * AES对象
     */
    private AES aes;

    /**
     * 获取单例
     */
    @Getter
    private static final AesManager instance = new AesManager();

    /**
     * 生成密匙库
     */
    public void generateKeyStore() {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String sourcePath = this.getKeyStoreOutPath(loader);
        if (new File(sourcePath).exists()) {
            logger.error("密匙库已存在，不可重新生成", new FileAlreadyExistsException("密匙库已存在，不可重新生成"));
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(sourcePath)) {
            // 生成随机密匙
            SecretKey secretKey = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue());
            char[] keyStorePassword = this.getKeyStorePassword();

            // 生成密匙库
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, keyStorePassword);

            // 填入key
            keyStore.setKeyEntry(ALIAS, secretKey, keyStorePassword, null);
            keyStore.store(fos, keyStorePassword);

            String key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            logger.info("密匙已生成, 请妥善保管: {}", key);
        } catch (IOException e) {
            logger.error("目标文件生成失败: {}", e, sourcePath);
        } catch (KeyStoreException e) {
            logger.error("KeyStore解析失败: {}", e, sourcePath);
        } catch (NoSuchAlgorithmException e) {
            logger.error("未知的加密算法", e);
        } catch (CertificateException e) {
            logger.error("", e);
        }
    }

    /**
     * 加密
     */
    public String encrypt(final String plaintext) {
        return this.getAes().encryptHex(plaintext);
    }

    /**
     * 解密（Java端解密）
     */
    public String decrypt(final String ciphertext) {
        return this.getAes().decryptStr(ciphertext, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 获取AES实例
     */
    private AES getAes() {
        if (this.aes == null) {
            this.analysisKey();
        }
        return this.aes;
    }

    /**
     * 从KeyStore中获取密匙
     */
    private void analysisKey() {

        try {
            if (StringUtil.isNotBlank(aesKey)) {
                return;
            }
            InputStream stream = ResourceUtil.getStream(KEY_STORE);
            char[] keyStorePassword = this.getKeyStorePassword();

            // 读取密匙库
            KeyStore keyStore = SecureUtil.readJKSKeyStore(stream, keyStorePassword);
            Key secureKey = keyStore.getKey(ALIAS, keyStorePassword);

            aesKey = Base64.getEncoder().encodeToString(secureKey.getEncoded());
            this.aes = SecureUtil.aes(secureKey.getEncoded());
        } catch (Exception e) {
            logger.error("AES密匙库加载失败", e);
        }
    }

    /**
     * ECB解密（用于前端Crypto库进行加密的数据解密）
     */
    public String ecbDecrypt(final String key, final String data) {
        AES ecbAes = new AES(Mode.ECB, Padding.PKCS5Padding, Base64.getDecoder().decode(key));
        return ecbAes.decryptStr(data);
    }

    /**
     * 获取KeyStore的输出路径
     */
    private String getKeyStoreOutPath(final ClassLoader loader) {
        URL classes = loader.getResource("");
        assert classes != null;

        String target = new File(classes.getPath()).getParent();
        String project = new File(target).getParent();
        return project + RESOURCES + KEY_STORE;
    }

    /**
     * 密匙库密码
     */
    private char[] getKeyStorePassword() {
        return "9YtNs49vjEMGh8SLDDxMq8sNY2nqQu8s3PCUyNWAYyq".toCharArray();
    }
}
