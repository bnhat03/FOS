package com.example.BE_PBL6_FastOrderSystem.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class PromotionResponse {
    private Long id;
    private String name;
    private String description;
    private String image;
    private double discountPercentage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Long> storeIds;
    private List<String> storeNames;


    public PromotionResponse(Long id, String name, String description, String image, double discountPercentage, LocalDateTime startDate, LocalDateTime endDate, List<Long> storeIds, List <String> storeNames) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.storeIds = storeIds;
        this.storeNames = storeNames;
    }

}