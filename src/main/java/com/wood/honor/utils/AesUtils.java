package com.wood.honor.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @author: xu.dm
 * @since: 2018/11/24 22:26
 *
 */
public class AesUtils {
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    public static String encrypt(String content, String key) {
        try {
            //获得密码的字节数组
            byte[] raw = key.getBytes();
            //根据密码生成AES密钥
            SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
            //根据指定算法ALGORITHM自成密码器
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //初始化密码器，第一个参数为加密(ENCRYPT_MODE)或者解密(DECRYPT_MODE)操作，第二个参数为生成的AES密钥
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            //获取加密内容的字节数组(设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte [] contentBytes = content.getBytes(StandardCharsets.UTF_8);
            //密码器加密数据
            byte [] encodeContent = cipher.doFinal(contentBytes);
            //将加密后的数据转换为字符串返回
            return Base64.encodeBase64String(encodeContent);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("AesUtils加密失败");
        }
    }

    public static String decrypt(String encryptStr, String decryptKey) {
        try {
            //获得密码的字节数组
            byte[] raw = decryptKey.getBytes();
            //根据密码生成AES密钥
            SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
            //根据指定算法ALGORITHM自成密码器
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //初始化密码器，第一个参数为加密(ENCRYPT_MODE)或者解密(DECRYPT_MODE)操作，第二个参数为生成的AES密钥
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            //把密文字符串转回密文字节数组
            byte [] encodeContent = Base64.decodeBase64(encryptStr);
            //密码器解密数据
            byte [] byteContent = cipher.doFinal(encodeContent);
            //将解密后的数据转换为字符串返回
            return new String(byteContent, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("AesUtils解密失败");
        }
    }
}
