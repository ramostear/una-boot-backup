package com.ramostear.unaboot.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName EncryptUtils
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/23 0023 4:30
 * @Version 1.0
 **/
public class EncryptUtils {

    private EncryptUtils(){}

    private static final String SALT = "1VJOQ78GPCMPFMVD3E0PLDOL5Q";

    public static String encrypt(String source){
        return encrypt(source,SALT);
    }

    public static String encrypt(String source,String salt){
        return md5(md5(salt)+md5(source));
    }

    private static String md5(String input){
        byte[] code = null;
        try {
            code = MessageDigest.getInstance("md5").digest(input.getBytes());
        }catch (NoSuchAlgorithmException e){
            code = input.getBytes();
        }
        BigInteger bigInteger = new BigInteger(code);
        return bigInteger.abs().toString(8).toUpperCase();
    }
}
