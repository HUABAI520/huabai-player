//package com.ithe.huabaiplayer.jiami;
//
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.junit.jupiter.api.Test;
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.Mac;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.io.UnsupportedEncodingException;
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.security.Security;
//import java.util.HexFormat;
//
///**
// * @ClassName JiaMi
// * @Author hua bai
// * @Date 2024/10/10 21:38
// **/
//public class JiaMi {
//    private final static String HEX_STRING = "0123456789ABCDEF";
//
//    @Test
//    public void test1() throws NoSuchAlgorithmException, UnsupportedEncodingException {
//        MessageDigest md5 = MessageDigest.getInstance("MD5");
//        md5.update((HEX_STRING + "HelloWorld").getBytes(StandardCharsets.UTF_8));
//        byte[] result = md5.digest();
//        System.out.println(HexFormat.of().formatHex(result));
//
//        Security.addProvider(new BouncyCastleProvider());
//        // 按名称正常调用:
//        MessageDigest md = MessageDigest.getInstance("RipeMD160");
//        md.update(("HelloWorld" + HEX_STRING).getBytes(StandardCharsets.UTF_8));
//        byte[] result1 = md.digest();
//        System.out.println(HexFormat.of().formatHex(result1));
//    }
//
//    @Test
//    public void test2() throws Exception {
//        KeyGenerator keyGen = KeyGenerator.getInstance("HmacMD5");
//        SecretKey key = keyGen.generateKey();
//        // 打印随机生成的key:
//        byte[] skey = key.getEncoded();
//        System.out.println(HexFormat.of().formatHex(skey));
//        Mac mac = Mac.getInstance("HmacMD5");
//        mac.init(key);
//        mac.update("HelloWorld".getBytes(StandardCharsets.UTF_8));
//        byte[] result = mac.doFinal();
//        System.out.println(HexFormat.of().formatHex(result));
//
//    }
//    @Test
//    public void test3() throws Exception {
//        byte[] hkey = HexFormat.of().parseHex(
//                "a95941dc67db2aadce3152ee3e777c862adb9cd09046442d1657edcb63e236c3f3dfca5f04e0754337a8d423f7c007fb7741aade76b4326e5a2d48b674c5f355");
//        SecretKey key = new SecretKeySpec(hkey, "HmacMD5");
//        Mac mac = Mac.getInstance("HmacMD5");
//        mac.init(key);
//        mac.update("HelloWorld".getBytes(StandardCharsets.UTF_8));
//        byte[] result = mac.doFinal();
//        System.out.println(HexFormat.of().formatHex(result));
//    }
//}
