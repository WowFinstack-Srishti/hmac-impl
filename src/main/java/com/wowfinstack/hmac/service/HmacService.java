package com.wowfinstack.hmac.service;

public interface HmacService {
    String generateSignature(String payload);
    boolean isValidSignature(String payload, String providedSignature);
}
