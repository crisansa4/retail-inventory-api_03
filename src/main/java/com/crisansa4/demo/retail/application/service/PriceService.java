package com.crisansa4.demo.retail.application.service;

import com.crisansa4.demo.retail.domain.Price;
import com.crisansa4.demo.retail.application.port.PricePort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PriceService {

    private final PricePort pricePort;

    public PriceService(PricePort pricePort) {
        this.pricePort = pricePort;
    }

    public List<Price> getAllPrices() {
        return pricePort.findAll();
    }

    public Price getPriceById(Long id) {
        return pricePort.findById(id).orElse(null);
    }

    public Price createPrice(Price price){
        return pricePort.save(price);
    }

    public Price findApplicablePrice(LocalDateTime date, int brandId, int productId) {
        return pricePort.findByBrandIdAndProductIdAndDate(brandId, productId, date).stream()
                .max((p1, p2) -> Integer.compare(p1.getPriority(), p2.getPriority()))
                .orElse(null);
    }
}
