package com.crisansa4.demo.retail.infrastructure.controller;

import com.crisansa4.demo.retail.application.service.PriceService;
import com.crisansa4.demo.retail.domain.model.Price;
import com.crisansa4.demo.retail.infrastructure.controller.dto.ApplicablePriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApplicationController {

    private final PriceService priceService;

    @Autowired
    public ApplicationController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/prices")
    public ResponseEntity<List<Price>> getAllPrices() {
        List<Price> prices = priceService.getAllPrices();
        return ResponseEntity.ok(prices);
    }

    @GetMapping("/prices/{id}")
    public ResponseEntity<Price> getPriceById(@PathVariable Long id) {
        Price price = priceService.getPriceById(id);
        if (price == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(price);
    }

    @PostMapping("/prices")
    public ResponseEntity<Price> createPrice(@RequestBody Price price){
        Price createdPrice = priceService.createPrice(price);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPrice);
    }

    @GetMapping("/applicablePrice")
    public ResponseEntity<ApplicablePriceResponse> getApplicablePrice (@RequestParam LocalDateTime dateTime, @RequestParam int brandId, @RequestParam int productId) {
        Price price = priceService.findApplicablePrice(dateTime, brandId, productId);

        if (price == null) {
            return ResponseEntity.notFound().build();
        }

        ApplicablePriceResponse response = new ApplicablePriceResponse();
        response.setProductId(price.getProductId());
        response.setBrandId(price.getBrandId());
        response.setPriceList(price.getPriceList());
        response.setStartDate(price.getStartDate());
        response.setEndDate(price.getEndDate());
        response.setFinalPrice(price.getPrice());
        response.setCurrency(price.getCurrency());

        return ResponseEntity.ok(response);
    }
}
