package com.wss.common.secret;//package com.xincheng.common.secret;
//
//
//import com.orhanobut.logger.Logger;
//import com.xincheng.common.constants.Constants;
//
//
//import java.security.Security;
//import java.security.spec.AlgorithmParameterSpec;
//import java.util.Objects;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//
///**
// * Describe：AES 加解密操作
// * Created by 吴天强 on 2018-08-03 17:47
// **/
//public class AesUtils {
//    private static final String CHARSET_NAME = "UTF-8";
//    private static final String AES_NAME = "AES";
//    private static final String ALGORITHM = "AES/CBC/PKCS7Padding";
//    private static final String IV = Constants.AES.AES_IV;
//
//    static {
//        Security.addProvider(new BouncyCastleProvider());
//    }
//
//    /**
//     * 加密
//     */
//    public static String encrypt(String content) {
//        try {
//            Cipher cipher = Cipher.getInstance(ALGORITHM);
//            SecretKeySpec keySpec = new SecretKeySpec(Constants.AES.AES_KEY.getBytes(CHARSET_NAME), AES_NAME);
//            AlgorithmParameterSpec paramSpec = new IvParameterSpec(IV.getBytes());
//            cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
//            return ParseSystemUtil.parseByte2HexStr(cipher.doFinal(content.getBytes(CHARSET_NAME)));
//        } catch (Exception e) {
//            e.printStackTrace();
//            Logger.e("加密失败");
//        }
//        return "";
//    }
//
//    /**
//     * 解密
//     */
//    public static String decrypt(String content) {
//        try {
//            Cipher cipher = Cipher.getInstance(ALGORITHM);
//            SecretKeySpec keySpec = new SecretKeySpec(Constants.AES.AES_KEY.getBytes(CHARSET_NAME), AES_NAME);
//            AlgorithmParameterSpec paramSpec = new IvParameterSpec(IV.getBytes());
//            cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
//            return new String(cipher.doFinal(Objects.requireNonNull(ParseSystemUtil.parseHexStr2Byte(content))), CHARSET_NAME);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Logger.e("解密失败");
//        }
//        return "";
//    }
//
//}
//
//
