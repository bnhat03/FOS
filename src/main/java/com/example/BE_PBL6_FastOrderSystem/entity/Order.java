package com.example.BE_PBL6_FastOrderSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String orderCode;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
    private LocalDateTime orderDate;
    private Double totalAmount;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusOrder status;
    private String deliveryAddress;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "latitude")
    private Double latitude;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Column(name = "feed_back",nullable = false)
    private Boolean feedback = false;
    @ManyToOne
    @JoinColumn(name = "discount_code_id")
    private Voucher voucher;
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderCode='" + orderCode + '\'' +
                ", user=" + user +
                ", orderDetails=" + orderDetails +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", feedBack=" + feedback +
                ", voucher=" + voucher +
                '}';
    }
}