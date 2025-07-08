package com.wowfinstack.hmac.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class HmacUtil {
    public static String generateHmac(String payload, String secretKey) {
        try{
            String combined = payload + secretKey;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(combined.getBytes(StandardCharsets.UTF_8));
            String upperHex = bytesToHex(hash).toUpperCase();
            return Base64.getEncoder().encodeToString(upperHex.getBytes(StandardCharsets.UTF_8));
        }catch(Exception e){
            throw new RuntimeException("Failed to generate HMAC", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
