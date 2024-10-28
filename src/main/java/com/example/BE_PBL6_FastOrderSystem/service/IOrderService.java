package com.example.BE_PBL6_FastOrderSystem.service;

import com.example.BE_PBL6_FastOrderSystem.entity.*;
import com.example.BE_PBL6_FastOrderSystem.response.APIRespone;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOrderService {
    String generateUniqueOrderCode();

    Double getPriceBasedProductOnSize(Product product, Size size);

    Double getPriceBasedComboOnSize(Combo combo, Size size);

    ResponseEntity<APIRespone> findNearestShipper(Double latitude, Double longitude, int limit);

    ResponseEntity<APIRespone> processOrder(Long userId, String paymentMethod, List<Long> cartIds, String deliveryAddress,Double longitude, Double latitude, String orderCode, String discountCode);

    ResponseEntity<APIRespone> processOrderNow(Long userId, String paymentMethod, Long productId, Long comboId, List<Long> drinkId, Long storeId, Integer quantity, String size, String deliveryAddress,Double longitude, Double latitude, String orderCode, String discountCode);

    Double calculateShippingFee(Order order, Store store);

    Long calculateOrderNowAmount(Long productId, Long comboId, int quantity, Long storeId, Double Latitude, Double Longitude, String DiscountCode, String size);


    Long calculateOrderAmount(List<Long> cartIds, Double latitude, Double longitude, String discountCode);

    ResponseEntity<APIRespone> updateQuantityProduct(Long productId, Long comboId, Long storeId, int quantity);
    ResponseEntity<APIRespone> updateOrderStatus(String orderCode, String status);


    ResponseEntity<APIRespone> updateOrderDetailStatus(String orderCode, Long storeId, String status);

    ResponseEntity<APIRespone> getAllOrderDetailOfStore(Long ownerId);

    ResponseEntity<APIRespone> getOrderDetailOfStore(Long ownerId, String orderCode);


    ResponseEntity<APIRespone> getAllOrderDetailsByUser(Long userId);

    ResponseEntity<APIRespone> getOrderDetailByUserId(Long userId, String orderCode);

    ResponseEntity<APIRespone> cancelOrder(String orderCode, Long serId);
    List<Cart> getCartItemsByCartId(Long cartId);
    ResponseEntity<APIRespone> findOrderByOrderCode(String orderCode);
    ResponseEntity<APIRespone> getOrdersByStatusAndUserId(String status, Long userId);
    ResponseEntity<APIRespone>  findOrderByOrderCodeAndUserId(String orderCode, Long userId);
    ResponseEntity<APIRespone> getAllOrderByStatusOfStore(String statusName, Long OwnerId);
    ResponseEntity<APIRespone> getOrderByStatus(Long ownerId, String status);
    ResponseEntity<APIRespone> updateStatusFeedBack(Long orderid);

}