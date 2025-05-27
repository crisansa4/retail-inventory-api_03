package com.crisansa4.demo.retail.infrastructure.repository.mapper;

import com.crisansa4.demo.retail.domain.Price;
import com.crisansa4.demo.retail.infrastructure.repository.model.PriceEntity;

public class PriceMapper {
    public static Price toDomain(PriceEntity entity) {
        Price price = new Price();
        price.setId(entity.getId());
        price.setBrandId(entity.getBrandId());
        price.setStartDate(entity.getStartDate());
        price.setEndDate(entity.getEndDate());
        price.setPriceList(entity.getPriceList());
        price.setProductId(entity.getProductId());
        price.setPriority(entity.getPriority());
        price.setPrice(entity.getPrice());
        price.setCurr(entity.getCurr());
        return price;
    }

    public static PriceEntity toEntity(Price price) {
        PriceEntity entity = new PriceEntity();
        entity.setId(price.getId());
        entity.setBrandId(price.getBrandId());
        entity.setStartDate(price.getStartDate());
        entity.setEndDate(price.getEndDate());
        entity.setPriceList(price.getPriceList());
        entity.setProductId(price.getProductId());
        entity.setPriority(price.getPriority());
        entity.setPrice(price.getPrice());
        entity.setCurr(price.getCurr());
        return entity;
    }
}
