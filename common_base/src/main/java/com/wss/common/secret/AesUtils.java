package com.wss.common.secret;//package com.xincheng.common.secret;


import com.wss.common.profile.ProfileManager;

import org.bouncycastle.util.encoders.Base64;

import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * Describe：AES 加解密操作
 * Created by 吴天强 on 2018-08-03 17:47
 **/
public class AesUtils {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String KEY = ProfileManager.profile().getAesSecretKey();

    private static AesUtils aesUtils;

    private AesUtils() {
    }

    public static AesUtils getInstance() {
        if (aesUtils == null) {
            synchronized (AesUtils.class) {
                if (aesUtils == null) {
                    aesUtils = new AesUtils();
                }
            }
        }
        return aesUtils;
    }

    /**
     * 获取秘钥方法
     */
    private byte[] getKey() {
        KeyGenerator kg;
        try {
            kg = KeyGenerator.getInstance("AES");
            kg.init(192);
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            System.out.println("KEY---------" + new String(Base64.encode(b)));
            return b;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES加密方法
     *
     * @param str 需要加密的字符串
     * @return 加密后数据
     */
    public String encrypt(String str) {
        byte[] result;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //生成加密解密需要的Key
            SecretKeySpec keySpec = new SecretKeySpec(Base64.decode(KEY.getBytes()), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            result = cipher.doFinal(str.getBytes("UTF-8"));
            return URLEncoder.encode(new String(Base64.encode(result)), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * AES解密方法
     *
     * @param str 需要加密的字符串
     * @return 解密后数据
     */
    public String decrypt(String str) {
        String result = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(Base64.decode(KEY.getBytes()), "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decoded = cipher.doFinal(Base64.decode(str.getBytes()));
            result = new String(decoded, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}


