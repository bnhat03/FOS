package com.example.BE_PBL6_FastOrderSystem.service.Impl;
import com.example.BE_PBL6_FastOrderSystem.entity.Product;
import com.example.BE_PBL6_FastOrderSystem.entity.Promotion;
import com.example.BE_PBL6_FastOrderSystem.entity.Store;
import com.example.BE_PBL6_FastOrderSystem.repository.ProductRepository;
import com.example.BE_PBL6_FastOrderSystem.repository.PromotionRepository;
import com.example.BE_PBL6_FastOrderSystem.repository.StoreRepository;
import com.example.BE_PBL6_FastOrderSystem.repository.UserRepository;
import com.example.BE_PBL6_FastOrderSystem.request.PromotionRequest;
import com.example.BE_PBL6_FastOrderSystem.response.APIRespone;
import com.example.BE_PBL6_FastOrderSystem.response.PromotionResponse;
import com.example.BE_PBL6_FastOrderSystem.service.IPromotionService;
import com.example.BE_PBL6_FastOrderSystem.utils.ImageGeneral;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements IPromotionService {
    private final PromotionRepository promotionRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<APIRespone> getAllPromotion() {
        if (promotionRepository.findAll().isEmpty()) {
            return new ResponseEntity<>(new APIRespone(false, "No promotion found", ""), HttpStatus.NOT_FOUND);
        }
        List<PromotionResponse> promotionResponses = promotionRepository.findAll().stream()
                .map(promotion -> new PromotionResponse(
                        promotion.getId(),
                        promotion.getName(),
                        promotion.getDescription(),
                        promotion.getImage(),
                        promotion.getDiscountPercentage(),
                        promotion.getStartDate(),
                        promotion.getEndDate(),
                        promotion.getStores().stream().map(store -> store.getStoreId()).collect(Collectors.toList()),
                        promotion.getStores().stream().map(store -> store.getStoreName()).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
        return new ResponseEntity<>(new APIRespone(true, "Success", promotionResponses), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIRespone> getPromotionById(Long promotionId) {
        Optional<Promotion> promotion = promotionRepository.findById(promotionId);
        if (promotion.isEmpty()) {
            return new ResponseEntity<>(new APIRespone(false, "Promotion not found", ""), HttpStatus.NOT_FOUND);
        }
        PromotionResponse promotionResponse = new PromotionResponse(
                promotion.get().getId(),
                promotion.get().getName(),
                promotion.get().getDescription(),
                promotion.get().getImage(),
                promotion.get().getDiscountPercentage(),
                promotion.get().getStartDate(),
                promotion.get().getEndDate(),
                promotion.get().getStores().stream().map(store -> store.getStoreId()).collect(Collectors.toList()),
                promotion.get().getStores().stream().map(store -> store.getStoreName()).collect(Collectors.toList())
        );
        return new ResponseEntity<>(new APIRespone(true, "Success", promotionResponse), HttpStatus.OK);


    }

    @Override
    public ResponseEntity<APIRespone> getAllPromoByStoreId(Long storeId) {
        Optional<Store> store = storeRepository.findById(storeId);
        if (store.isEmpty()) {
            return new ResponseEntity<>(new APIRespone(false, "Store not found", ""), HttpStatus.NOT_FOUND);
        }
        List<PromotionResponse> promotionResponses = promotionRepository.findAll().stream()
                .filter(promotion -> promotion.getStores().contains(store.get()))
                .map(promotion -> new PromotionResponse(
                        promotion.getId(),
                        promotion.getName(),
                        promotion.getDescription(),
                        promotion.getImage(),
                        promotion.getDiscountPercentage(),
                        promotion.getStartDate(),
                        promotion.getEndDate(),
                        promotion.getStores().stream().map(store1 -> store1.getStoreId()).collect(Collectors.toList()), promotion.getStores().stream().map(store1 -> store1.getStoreName()).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
        return new ResponseEntity<>(new APIRespone(true, "Success", promotionResponses), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIRespone> addPromotion(PromotionRequest promotionRequest) {
        if (promotionRepository.existsByName(promotionRequest.getName())) {
            return new ResponseEntity<>(new APIRespone(false, "Promotion already exists", ""), HttpStatus.BAD_REQUEST);
        }
        Promotion promotion = new Promotion();
        promotion.setName(promotionRequest.getName());
        promotion.setDescription(promotionRequest.getDescription());
        try {
            InputStream imageInputStream = promotionRequest.getImage().getInputStream();
            String base64Image = ImageGeneral.fileToBase64(imageInputStream);
            promotion.setImage(base64Image);
        } catch (IOException e) {
            return new ResponseEntity<>(new APIRespone(false, "Error when upload image", ""), HttpStatus.BAD_REQUEST);
        }
        promotion.setDiscountPercentage(promotionRequest.getDiscountPercentage());
        promotion.setStartDate(promotionRequest.getStartDate());
        promotion.setEndDate(promotionRequest.getEndDate());
        promotionRepository.save(promotion);
        return new ResponseEntity<>(new APIRespone(true, "Promotion added successfully", ""), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIRespone> DeletePromotion(Long promotionId) {
        Optional<Promotion> promotion = promotionRepository.findById(promotionId);
        if (promotion.isEmpty()) {
            return new ResponseEntity<>(new APIRespone(false, "Promotion not found", ""), HttpStatus.NOT_FOUND);
        }
        promotionRepository.deleteById(promotionId);
        return new ResponseEntity<>(new APIRespone(true, "Promotion deleted successfully", ""), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIRespone> updatePromotion(Long promotionId, PromotionRequest promotionRequest) {
        if (promotionRepository.findById(promotionId).isEmpty()) {
            return new ResponseEntity<>(new APIRespone(false, "Promotion not found", ""), HttpStatus.NOT_FOUND);
        }
        Promotion promotion = promotionRepository.findById(promotionId).get();
        promotion.setName(promotionRequest.getName());
        promotion.setDescription(promotionRequest.getDescription());
        try {
            InputStream imageInputStream = promotionRequest.getImage().getInputStream();
            String base64Image = ImageGeneral.fileToBase64(imageInputStream);
            promotion.setImage(base64Image);
        } catch (IOException e) {
            return new ResponseEntity<>(new APIRespone(false, "Error when upload image", ""), HttpStatus.BAD_REQUEST);
        }
        promotion.setDiscountPercentage(promotionRequest.getDiscountPercentage());
        promotion.setStartDate(promotionRequest.getStartDate());
        promotion.setEndDate(promotionRequest.getEndDate());
        promotionRepository.save(promotion);
        return new ResponseEntity<>(new APIRespone(true, "Promotion updated successfully", ""), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIRespone> applyPromotionToStore(Long promotionId, Long storeId) {
        Optional<Promotion> promotionOptional = promotionRepository.findById(promotionId);
        if (promotionOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Promotion not found", ""));
        }
        Promotion promotion = promotionOptional.get();
        Optional<Store> storeOptional = storeRepository.findById(storeId);
        if (storeOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Store not found", ""));
        }
        Store store = storeOptional.get();
        promotion.getStores().add(store);
        promotionRepository.save(promotion);

        return ResponseEntity.ok(new APIRespone(true, "Promotion applied to store successfully", ""));
    }

    @Override
    public ResponseEntity<APIRespone> applyPromotionToAllStores(Long promotionId) {
        Optional<Promotion> promotionOptional = promotionRepository.findById(promotionId);
        if (promotionOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Promotion not found", ""));
        }
        Promotion promotion = promotionOptional.get();
        List<Store> stores = storeRepository.findAll();
        if (promotion.getStores().containsAll(stores)) {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Promotion already applied to all stores", ""));
        }
        stores.forEach(store -> applyPromotionToStore(promotionId, store.getStoreId()));
        promotionRepository.save(promotion);
        return ResponseEntity.ok(new APIRespone(true, "Promotion applied to all stores successfully", ""));
    }

    @Override
    public ResponseEntity<APIRespone> applyPromotionsToStore(List<Long> promotionIds, Long storeId) {
        Optional<Store> storeOptional = storeRepository.findById(storeId);
        if (storeOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Store not found", ""));
        }
        Store store = storeOptional.get();
        List<Promotion> promotions = promotionRepository.findAllById(promotionIds);
        if (promotions.isEmpty()) {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Promotions not found", ""));
        }
        List<String> alreadyAppliedPromotions = new ArrayList<>();
        int count = 0;
        for (Promotion promotion : promotions) {
            if (promotion.getStores().contains(store)) {
                alreadyAppliedPromotions.add(promotion.getName());
                count++;
            } else {
                promotion.getStores().add(store);
                store.getPromotions().add(promotion);
            }
        }
        if (count == promotions.size()) {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Promotions already applied to store", ""));
        }
        promotionRepository.saveAll(promotions);
        storeRepository.save(store);
        return ResponseEntity.ok(new APIRespone(true, "Promotions applied to store successfully", ""));
    }


    @Override
    public ResponseEntity<APIRespone> applyPromotionToProduct(Long promotionId, Long productId) {
        Optional<Promotion> promotionOptional = promotionRepository.findById(promotionId);
        if (promotionOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Promotion not found with id " + promotionId, ""));
        }
        Promotion promotion = promotionOptional.get();
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Product not found with id " + productId, ""));
        }
        if (productOptional.get().getPromotions().contains(promotion)) {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Promotion already applied to product", ""));
        }
        Product product = productOptional.get();
        boolean isPromotionInProductStores = product.getProductStores().stream()
                .anyMatch(productStore -> promotion.getStores().contains(productStore.getStore()));
        if (isPromotionInProductStores) {
            product.getPromotions().add(promotion);
            promotion.getProducts().add(product);
            // Calculate discounted price
            double maxDiscountPercentage = product.getPromotions().stream()
                    .mapToDouble(Promotion::getDiscountPercentage)
                    .max()
                    .orElse(0);
            product.setDiscountedPrice(product.getPrice() * (1 - maxDiscountPercentage / 100));
            productRepository.save(product);
            promotionRepository.save(promotion);
            return ResponseEntity.ok(new APIRespone(true, "Promotion applied to product successfully", ""));
        } else {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Promotion does not belong to the product's store", ""));
        }
    }
    @Override
    public ResponseEntity<APIRespone> removePromotionsFromStore(List<Long> promotionIds, Long storeId) {
        Optional<Store> storeOptional = storeRepository.findById(storeId);
        if (storeOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Store not found", ""));
        }
        Store store = storeOptional.get();
        List<Promotion> promotions = promotionRepository.findAllById(promotionIds);
        if (promotions.isEmpty()) {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Promotions not found", ""));
        }
        List<String> notAppliedPromotions = new ArrayList<>();
        int count = 0;
        for (Promotion promotion : promotions) {
            if (!promotion.getStores().contains(store)) {
                notAppliedPromotions.add(promotion.getName());
                count++;
            } else {
                promotion.getStores().remove(store);
                store.getPromotions().remove(promotion);
            }
        }
        if (count == promotions.size()) {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Promotions not applied to store", ""));
        }
        promotionRepository.saveAll(promotions);
        storeRepository.save(store);
        return ResponseEntity.ok(new APIRespone(true, "Promotions removed from store successfully", ""));
    }


    @Override
    public ResponseEntity<APIRespone> applyPromotionToListProducts(Long promotionId, List<Long> productIds) {
        Optional<Promotion> promotionOptional = promotionRepository.findById(promotionId);
        if (promotionOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Promotion not found with id " + promotionId, ""));
        }
        Promotion promotion = promotionOptional.get();
        List<Product> products = productRepository.findAllById(productIds);
        if (products.isEmpty()) {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Products not found", ""));
        }
        List<Product> productsToApply = products.stream()
                .filter(product -> product.getProductStores().stream()
                        .anyMatch(productStore -> promotion.getStores().contains(productStore.getStore())))
                .collect(Collectors.toList());
        if (productsToApply.isEmpty()) {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Promotion does not belong to any of the products' stores", ""));
        }
        productsToApply.forEach(product -> {
            if (!product.getPromotions().contains(promotion)) {
                product.getPromotions().add(promotion);
                promotion.getProducts().add(product);
                // Calculate discounted price
                double maxDiscountPercentage = product.getPromotions().stream()
                        .mapToDouble(Promotion::getDiscountPercentage)
                        .max()
                        .orElse(0);
                product.setDiscountedPrice(product.getPrice() * (1 - maxDiscountPercentage / 100));
            }
        });
        productRepository.saveAll(productsToApply);
        promotionRepository.save(promotion);
        return ResponseEntity.ok(new APIRespone(true, "Promotion applied to products successfully", ""));

    }

    @Override
    public ResponseEntity<APIRespone> removePromotionFromProduct(Long promotionId, Long productId) {
        Optional<Promotion> promotionOptional = promotionRepository.findById(promotionId);
        if (promotionOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Promotion not found with id " + promotionId, ""));
        }
        Promotion promotion = promotionOptional.get();
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Product not found with id " + productId, ""));
        }
        Product product = productOptional.get();
        if (!product.getPromotions().contains(promotion)) {
            return ResponseEntity.badRequest().body(new APIRespone(false, "Promotion not applied to product", ""));
        }
        // Remove promotion from product
        product.getPromotions().remove(promotion);
        promotion.getProducts().remove(product);
        // Calculate discounted price after removing promotion = null if no promotion
        if (product.getPromotions().isEmpty()) {
            product.setDiscountedPrice(0.0);
        } else {
            double maxDiscountPercentage = product.getPromotions().stream()
                    .mapToDouble(Promotion::getDiscountPercentage)
                    .max()
                    .orElse(0);
            product.setDiscountedPrice(product.getPrice() * (1 - maxDiscountPercentage / 100));
        }
        productRepository.save(product);
        promotionRepository.save(promotion);
        return ResponseEntity.ok(new APIRespone(true, "Promotion removed from product successfully", ""));
    }
}