package cn.wxxlamp.diary.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * @author wxxlamp
 * @date 2021/12/19~17:21
 */
public class EncryptUtils {

    /** 加密工具 */
    private static Cipher encryptCipher;

    /** 解密工具 */
    private static Cipher decryptCipher;

    static {
        try {
            encryptCipher = Cipher.getInstance("DES");
            decryptCipher = Cipher.getInstance("DES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对秘钥进行加密
     * <br/> 只会在系统启动时调用
     * @param strKey 秘钥，用于秘钥配置为空的情况，需要用户自行判断
     * @param encryptStr 加密字符串
     */
    public static String encryptWithKey(String strKey, String encryptStr) {
        String encryptKey = null;
        Key key = getKey(strKey.getBytes(StandardCharsets.UTF_8));
        try {
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
            encryptKey = byteArr2HexStr(encrypt(encryptStr.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptKey;
    }

    /**
     * 对秘钥进行解密，需要在系统启动的时候调用
     * @param strKey 秘钥
     * @return 解密值
     */
    public static String decryptWithKey(String strKey, String encryptStr) {
        String decryptKey = null;
        Key key = getKey(strKey.getBytes(StandardCharsets.UTF_8));
        try {
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
            decryptKey = new String(decrypt(hexStr2ByteArr(encryptStr)));
        } catch (BadPaddingException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptKey;
    }
    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     * @param arrB  需要转换的byte数组
     * @return 转换后的字符串
     */
    public static String byteArr2HexStr(byte[] arrB) {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuilder sb = new StringBuilder(iLen * 2);
        for (int b : arrB) {
            int intTmp = b;
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }
    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }
    /**
     * 加密字节数组
     * @param arrB  需加密的字节数组
     * @return 加密后的字节数组
     */
    public static byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);
    }

    /**
     * 加密字符串
     * @param strIn  需加密的字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String strIn) {
        try {
            return byteArr2HexStr(encrypt(strIn.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strIn;
    }
    /**
     * 解密字节数组
     * @param arrB  需解密的字节数组
     * @return 解密后的字节数组
     */
    public static byte[] decrypt(byte[] arrB) throws Exception {
        return decryptCipher.doFinal(arrB);
    }
    /**
     * 解密字符串
     * @param strIn  需解密的字符串
     * @return 解密后的字符串
     */
    public static String decrypt(String strIn) {
        try {
            return new String(decrypt(hexStr2ByteArr(strIn)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strIn;
    }
    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
     * @param arrTmp  构成该字符串的字节数组
     * @return 生成的密钥
     */
    private static Key getKey(byte[] arrTmp) {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];
        // 将原始字节数组转换为8位
        for (int i = 0; i < arrTmp.length && i < arrB.length; i++) {
            arrB[i] = arrTmp[i];
        }
        // 生成密钥
        return new SecretKeySpec(arrB, "DES");
    }
}
