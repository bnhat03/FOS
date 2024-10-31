package com.example.BE_PBL6_FastOrderSystem.response;

import com.example.BE_PBL6_FastOrderSystem.entity.ShipperOrder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@RequiredArgsConstructor
@Data
public class ShipperOrderResponse {
    private Long shipperOrderId;
    private Long shipperId;
    private String status;
    private LocalDateTime receivedAt;
    private LocalDateTime deliveredAt;
    private Long storeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long orderId;
    private String orderCode;
    private Long userId;
    private String fullName;
    private String phone;
    private Integer rewardPoints;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private Double shippingFee;
    private String deliveryAddress;
    private Double longitude;
    private Double latitude;
    private List<OrderDetailResponse> orderDetails;


    public ShipperOrderResponse(ShipperOrder shipperOrder) {
        this.shipperOrderId = shipperOrder.getId();
        this.shipperId = shipperOrder.getShipper().getId();
        this.status = shipperOrder.getStatus().getStatusName().isEmpty() ? null : shipperOrder.getStatus().getStatusName();
        this.receivedAt = shipperOrder.getReceivedAt();
        this.deliveredAt = shipperOrder.getDeliveredAt();
        this.storeId = shipperOrder.getStore().getStoreId();
        this.createdAt = shipperOrder.getCreatedAt();
        this.updatedAt = shipperOrder.getUpdatedAt();
        this.orderId = shipperOrder.getOrderDetails().get(0).getOrder().getOrderId();
        this.orderCode = shipperOrder.getOrderDetails().get(0).getOrder().getOrderCode();
        this.userId = shipperOrder.getOrderDetails().get(0).getOrder().getUser().getId();
        this.fullName = shipperOrder.getOrderDetails().get(0).getOrder().getUser().getFullName();
        this.phone = shipperOrder.getOrderDetails().get(0).getOrder().getUser().getPhoneNumber();
        this.rewardPoints = shipperOrder.getOrderDetails().get(0).getOrder().getUser().getRewardPoints();
        this.orderDate = shipperOrder.getOrderDetails().get(0).getOrder().getOrderDate();
        this.totalAmount = shipperOrder.getOrderDetails().stream()
                .filter(orderDetail -> orderDetail.getStore().getStoreId().equals(this.storeId))
                .mapToDouble(orderDetail -> orderDetail.getTotalPrice())
                .sum();
        this.shippingFee = shipperOrder.getOrderDetails().isEmpty() || shipperOrder.getOrderDetails().get(0).getShippingFee() == null
                ? null
                : shipperOrder.getOrderDetails().get(0).getShippingFee().getFee();
        this.deliveryAddress = shipperOrder.getOrderDetails().get(0).getOrder().getDeliveryAddress();
        this.longitude = shipperOrder.getOrderDetails().get(0).getOrder().getLongitude();
        this.latitude = shipperOrder.getOrderDetails().get(0).getOrder().getLatitude();
        this.orderDetails = shipperOrder.getOrderDetails().stream()
                .map(OrderDetailResponse::new)
                .collect(java.util.stream.Collectors.toList());

    }


}
