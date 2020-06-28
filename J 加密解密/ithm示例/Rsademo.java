package com.itheima.androidl.utils;

import java.util.Map;

public class Rsademo {

    public static void main(String[] args) {

        //私钥要长得多
//        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKB8GunT65dDzvV4" + "VD6UA9+lGEjIgEtyEVtrp3rEhBRmvOZ1sromkybrAF4ByodHh1BmgBLdImMqMzH2"
//                + "vgwc3ioOqiaODqHNPpqa/jeSrdNE/hJSKQqPXi+qVaIg6tOi84GnirHOrwkVxR45" + "kQgj4lH7qnIaMhooaIModIsDTGs7AgMBAAECgYEAg/Jlwlhtu9mRgDslsKnLoYZA"
//                + "uB65dM5dPPf/JC4MliV+LFEa2Hg8xmOy0pfQZ3dE5rLPnDLaQgQBQZQn3xehBE/N" + "2YdzLEH1Dpw1eOJY30Qf/Rp6jUaTwY5gQCxSDt24CXpDjzo09dvaR4uHhRNZX1KB"
//                + "XNco+PiM7ujFaSrhuBkCQQDQC3d2OhQB4vAaxaPzwqQv6lAFWCR8Osy5jyY/KlVF" + "kd/VzXp2uWACgFm6UXmUwyLfrSpFl013E5SjOsdgpXYNAkEAxXoqed1TYAHHS63N"
//                + "oIQlMz/ygHiMtkFeoD8HgKYw5TzYCpqlM++2O1VcbTLjQtnwctIe3B3xF7eOZ1Si" + "53KcZwJAdPaNYhWC3BCnJpYI9+ls/1c/R9HnKUSxhn05Zne5WxSJAB22hPrxRFa+"
//                + "m2Zk8ULH33LuehN3RMPoY+CO6QH9HQJBAK9+JrtP7iU2z2a42TEZ3nlSDe8PsnTR" + "WQdtm/w/NNqznIan8cJa+AZ4kH/WplIlneJcSuJwlW3vSNUZSQAIQWcCQHBsB41q"
//                + "WyyPcRBjMCR6YO4Iih/07kZJDAqHrdnhea+aNF+MPuShqIGlcDEbdeS1XxUp8gSm" + "diXEh5aJvpTfSEY=";
//
//        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgfBrp0+uXQ871eFQ+lAPfpRhI" + "yIBLchFba6d6xIQUZrzmdbK6JpMm6wBeAcqHR4dQZoAS3SJjKjMx9r4MHN4qDqom"
//                + "jg6hzT6amv43kq3TRP4SUikKj14vqlWiIOrTovOBp4qxzq8JFcUeOZEII+JR+6py" + "GjIaKGiDKHSLA0xrOwIDAQAB";


        try {
            Map<String, Object> genKeyPair = EncryptRSA.genKeyPair();
            String privateKey = EncryptRSA.getPrivateKey(genKeyPair);
            String publicKey = EncryptRSA.getPublicKey(genKeyPair);
            System.out.println("publicKey:" + publicKey);
            System.out.println("privateKey:" + privateKey);
            String source = "hyx_912_74571852356eb1dd4902f66c9c7ad103e8285d010d27488b";
            System.out.println("原文字：\r\n" + source);
            System.err.println("公钥加密——私钥解密");
            byte[] data = source.getBytes();
            byte[] encodedData = EncryptRSA.encryptByPublicKey(data, publicKey);
            byte[] decryptData = EncryptRSA.decryptByPrivateKey(encodedData, privateKey);
            System.out.println("公钥加密后：\r\n" + EncryptRSA.encode(encodedData));
            System.out.println("私钥解密后：\r\n" + new String(decryptData));

            System.err.println("私钥加密——公钥解密");
            byte[] encodedData1 = EncryptRSA.encryptByPrivateKey(data, privateKey);
            byte[] decryptData1 = EncryptRSA.decryptByPublicKey(encodedData1, publicKey);
            System.out.println("私钥加密后：\r\n" + EncryptRSA.encode(encodedData1));
            System.out.println("公钥解密后：\r\n" + new String(decryptData1));

            System.out.println("--------------签名,验证--------------");
            String str = "a同学向b同学了借了100w";
            String str1 = "a同学向b同学了借了1000w";
            // sign和verfy
            // 商户给支付宝
            String sign = EncryptRSA.sign(str.getBytes(), privateKey);// 想借钱的.a同学
            System.out.println("签名sign:\r\n" + sign);

            // boolean verify = RSACrypt.verify(原始内容, 和privatekey对应的公钥, 签名之后的内容);
            String content = str;//str1
            boolean verify = EncryptRSA.verify(content.getBytes(), publicKey, sign);// 借钱的人 b同学
            if (verify) {
                System.out.println(content + ":verfiy成功,确实是a同学给我打的欠条");
            } else {
                System.out.println(content + ":verfiy不成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
