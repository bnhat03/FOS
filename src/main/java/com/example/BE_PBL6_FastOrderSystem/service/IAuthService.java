package com.example.BE_PBL6_FastOrderSystem.service;

import com.example.BE_PBL6_FastOrderSystem.entity.User;
import com.example.BE_PBL6_FastOrderSystem.request.ShipperRequest;
import com.example.BE_PBL6_FastOrderSystem.response.APIRespone;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface IAuthService {
    ResponseEntity<APIRespone> authenticateUser(String numberPhone, String password);
    ResponseEntity<APIRespone> registerUser(User user);

    ResponseEntity<APIRespone> registerShipper(ShipperRequest shipperRequest);

    ResponseEntity<APIRespone> registerAdmin(User user);

    ResponseEntity<APIRespone> approveShipper(Long id);

    void logout(String token);
    boolean isTokenInvalid(String token);
    void invalidateToken(String refreshToken);
    ResponseEntity<APIRespone> SendOTP(String email);
    ResponseEntity<APIRespone> confirmOTP(String email, String otp, String newPassword);
}
