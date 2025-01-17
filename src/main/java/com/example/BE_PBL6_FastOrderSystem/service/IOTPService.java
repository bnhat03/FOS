package com.example.BE_PBL6_FastOrderSystem.service;

public interface IOTPService {
    String generateOTP(String email, Long userId);

    boolean verifyOTP(String email, String otp);

    String generateCodeShipper();

    boolean verifyCodeShipper(String code);

    void useCodeShipper(String code);
}
