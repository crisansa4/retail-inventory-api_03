package com.crisansa4.demo.retail.application.port;

import com.crisansa4.demo.retail.domain.Price;
import java.util.List;
import java.util.Optional;

public interface PricePort {
    List<Price> findAll();
    Optional<Price> findById(Long id);
    Price save(Price price);
}
