package com.example.BE_PBL6_FastOrderSystem.repository;

import com.example.BE_PBL6_FastOrderSystem.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    @Query("SELECT p FROM Voucher p WHERE p.startDate < ?1 AND p.endDate > ?1" )
    List<Voucher> findByStartDateBeforeAndEndDateAfter(LocalDateTime date);
    Optional<Voucher> findByCode(String code);
}
