package com.example.BE_PBL6_FastOrderSystem.security.user;

import com.example.BE_PBL6_FastOrderSystem.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodUserDetails implements UserDetails {
    private Long id;
    private String phoneNumber;
    private String password;
    private String fullName;
    private String email;
    private String sub;
    private String facebookId;
    private String address;
    private Double longitude;
    private Double latitude;
    private String avatar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean accountLocked;
    private Boolean isActive;
    private Collection<GrantedAuthority> authorities;

    public static FoodUserDetails buildUserDetails(User user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());
        return new FoodUserDetails(
                user.getId(),
                user.getPhoneNumber(),
                user.getPassword(),
                user.getFullName(),
                user.getEmail(),
                user.getSub(),
                user.getFacebookId(),
                user.getAddress(),
                user.getLongitude(),
                user.getLatitude(),
                user.getAvatar(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getAccountLocked(),
                user.getIsActive(),
                List.of(authority));
    }

    public static Long getCurrentUserId() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((FoodUserDetails) userDetails).getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked; // Trả về trạng thái khóa tài khoản
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } // Trả về trạng thái hết hạn của thông tin đăng nhập

    @Override
    public boolean isEnabled() {
        return true;
    }
}
