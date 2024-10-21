package com.example.BE_PBL6_FastOrderSystem.repository;

import com.example.BE_PBL6_FastOrderSystem.model.Order;
import com.example.BE_PBL6_FastOrderSystem.model.StatusOrder;

import com.example.BE_PBL6_FastOrderSystem.response.APIRespone;
import org.jooq.impl.QOM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsByOrderCode(String orderCode);

    @Query("SELECT o FROM Order o WHERE o.orderCode = ?1")
    Optional<Order> findByOrderCode(String orderCode);

    @Query("SELECT o FROM Order o WHERE o.orderId = ?1")
    Optional<Order> findByOrderId(Long orderId);

    List<Order> findAllByStatusAndUserId(StatusOrder statusOrder, Long userId);

    @Query("SELECT o FROM Order o WHERE o.status = ?1")
    boolean findByStatusOrder(StatusOrder statusOrder);

     @Query("SELECT o FROM Order o WHERE o.user.id = ?1")
     List<Order> findAllByUserId(Long userId);
    @Query("SELECT o FROM Order o")
    List<Order> findAlll();

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status.statusId = 5")
    Long getTotalAmountForCompletedOrders();

    @Query("SELECT COUNT(o) FROM Order o WHERE FUNCTION('MONTH', o.createdAt) = ?1 AND FUNCTION('YEAR', o.createdAt) = ?2")
    Long countOrdersByMonth(int month, int year);

    @Query("select sum(o.totalAmount) from Order o where function('MONTH',o.createdAt) = ?1 and function('YEAR', o.createdAt)= ?2")
    Long getTotalAmountByMonth(int month, int year);

    @Query("select sum(o.totalAmount) from Order o where function('MONTH',o.createdAt) = ?2 and function('DAY', o.createdAt) = ?1 and function('YEAR', o.createdAt)= ?3")
    Long getTotalAmountByWeek(int day, int month, int year);


}
