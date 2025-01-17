package com.example.BE_PBL6_FastOrderSystem.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class UserRequest {
    private String fullName;
    private MultipartFile avatar;
    private String email;
    private String address;

    public UserRequest(String fullName, MultipartFile avatar, String email, String address) {
        this.fullName = fullName;
        this.avatar = avatar;
        this.email = email;
        this.address = address;
    }

}