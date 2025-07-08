package com.wowfinstack.hmac.service.impl;

import com.wowfinstack.hmac.config.HmacConfig;
import com.wowfinstack.hmac.service.HmacService;
import com.wowfinstack.hmac.util.HmacUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HmacServiceImpl implements HmacService {

    @Autowired
    private HmacConfig hmacConfig;

    @Override
    public String generateSignature(String payload) {
        return HmacUtil.generateHmac(payload, hmacConfig.getSecretKey());
    }

    @Override
    public boolean isValidSignature(String payload, String providedSignature) {
        String calculated = generateSignature(payload);
        return calculated.equals(providedSignature);
    }
}
