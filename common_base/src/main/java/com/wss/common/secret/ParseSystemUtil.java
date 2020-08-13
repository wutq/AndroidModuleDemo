package com.wss.common.secret;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Describe：16进制与二进制转换类
 * Created by 吴天强 on 2018-08-03 16:31
 **/
public class ParseSystemUtil {
    /**
     * 将二进制转换成16进制
     *
     * @param buf 数据
     * @return String
     */
    @NotNull
    public static String parseByte2HexStr(@NotNull byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr 数据
     * @return byte[]
     */
    @Nullable
    public static byte[] parseHexStr2Byte(@NotNull String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}

    
