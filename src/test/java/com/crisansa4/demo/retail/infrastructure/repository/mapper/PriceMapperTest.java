package com.crisansa4.demo.retail.infrastructure.repository.mapper;

import com.crisansa4.demo.retail.domain.Price;
import com.crisansa4.demo.retail.infrastructure.repository.model.PriceEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceMapperTest {

    @Test
    void shouldMapEntityToDomain() {
        PriceEntity entity = new PriceEntity();
        entity.setId(1L);
        entity.setBrandId(1);
        entity.setStartDate(LocalDateTime.now());
        entity.setEndDate(LocalDateTime.now());
        entity.setPriceList(1);
        entity.setProductId(1);
        entity.setPriority(1);
        entity.setPrice(BigDecimal.valueOf(10.0));
        entity.setCurr("EUR");

        Price price = PriceMapper.toDomain(entity);
        assertEquals(entity.getId(), price.getId());
        assertEquals(entity.getCurr(), price.getCurr());
    }

    @Test
    void shouldMapDomainToEntity() {
        Price price = new Price();
        price.setId(2L);
        price.setBrandId(1);
        price.setStartDate(LocalDateTime.now());
        price.setEndDate(LocalDateTime.now());
        price.setPriceList(1);
        price.setProductId(1);
        price.setPriority(1);
        price.setPrice(BigDecimal.valueOf(20.0));
        price.setCurr("USD");

        PriceEntity entity = PriceMapper.toEntity(price);
        assertEquals(price.getId(), entity.getId());
        assertEquals(price.getCurr(), entity.getCurr());
    }
}
