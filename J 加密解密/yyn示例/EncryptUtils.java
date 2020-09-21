package com.actor.testapplication.utils;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 加密解密工具类
 */
public class EncryptUtils {

    private static final String key3DES = "abc1234_5*678km;Qbq/l!(o";

	//获取 3DES 的key
    public static byte[] build3DesKey(String keyStr) {
		//声明一个24位的字节数组
        byte[] key = new byte[24]; 
        try {
            byte[] temp = keyStr.getBytes("UTF-8");
            if (key.length > temp.length) {
                System.arraycopy(temp, 0, key, 0, temp.length);
            } else {
                //最长24位
                System.arraycopy(temp, 0, key, 0, key.length);
            }
        } catch (UnsupportedEncodingException e) {
        }
        return key;
    }

    //3DES加密
    public static String encode3DES(String message) {
        String result = "";
        try {
			//获得3DES密钥
            SecretKey secretKey = new SecretKeySpec(build3DesKey(key3DES), "DESede");
            //获得一个私鈅加密类Cipher，DESede是算法，ECB 是加密模式，PKCS5Padding是填充方式
            Cipher cipher = Cipher.getInstance("DESede");
			//设置工作模式为加密模式，传入密钥
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			//开始加密
            byte[] resultBytes = cipher.doFinal(message.getBytes("UTF-8"));
            BASE64Encoder enc = new BASE64Encoder();
			//BASE64编码
            result = enc.encode(resultBytes);
            //去掉加密后的换行符
            result = result.replaceAll("[\\r\\n]", "");
        } catch (Exception e) {
            return e.getMessage();
        }
        return result;
    }

    //3DES加密
    public static String encode3DES(String message, String key) {
        String result = "";
        try {
			//获得3DES密钥
            SecretKey secretKey = new SecretKeySpec(build3DesKey(key), "DESede");
            //获得一个私鈅加密类Cipher，DESede是算法，ECB 是加密模式，PKCS5Padding是填充方式
            Cipher cipher = Cipher.getInstance("DESede");
			//设置工作模式为加密模式，传入密钥
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			//开始加密
            byte[] resultBytes = cipher.doFinal(message.getBytes("UTF-8"));
            BASE64Encoder enc = new BASE64Encoder();
			//BASE64编码
            result = enc.encode(resultBytes);
            //去掉加密后的换行符
            result = result.replaceAll("[\\r\\n]", "");
        } catch (Exception e) {
            return e.getMessage();
        }
        return result;
    }

    //3DES解密
    public static String decode3DesECB(String message) {
        String result = "";
        try {
			//获得3DES密钥
            SecretKey secretKey = new SecretKeySpec(build3DesKey(key3DES), "DESede");
            BASE64Decoder dec = new BASE64Decoder();
			//BASE64解码
            byte[] messageBytes = dec.decodeBuffer(message);
            Cipher cipher = Cipher.getInstance("DESede");
			//设置工作模式为解密模式，传入密钥
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
			//开始解密
            byte[] resultBytes = cipher.doFinal(messageBytes);
            result = new String(resultBytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
