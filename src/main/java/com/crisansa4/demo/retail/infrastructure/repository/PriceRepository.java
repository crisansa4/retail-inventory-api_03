package com.crisansa4.demo.retail.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisansa4.demo.retail.infrastructure.repository.model.PriceEntity;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Long> {
    List<PriceEntity> findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
        int brandId, int productId, LocalDateTime date1, LocalDateTime date2);
}