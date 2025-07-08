package com.wowfinstack.hmac.controller;

import com.wowfinstack.hmac.service.HmacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/test")
public class HmacTestController {

    @Autowired
    private HmacService hmacService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> processRequest(@RequestBody Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> responseData = new HashMap<>();

        responseData.put("responseCode", "000");
        responseData.put("responseText", "Successfully Processed");
        responseData.put("refNo", body.get("refNo"));
        responseData.put("resDataTime", getDateTime());
        responseData.put("customerId", "C0830115950081697");
        responseData.put("cardId", "2019083000842477135");
        responseData.put("lmsid", "89999");

        response.put("response", responseData);

        String rawJson = response.toString().replaceAll("\\s+", "");
        String signature = hmacService.generateSignature(rawJson);

        return ResponseEntity.ok()
                .header("Sign", signature)
                .header("ResDatetime", getDateTime())
                .body(response);
    }

    private String getDateTime() {
        return DateTimeFormatter.ofPattern("ddMMyyyyHHmmss").format(LocalDateTime.now());
    }
}
