package com.crisansa4.demo.retail.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crisansa4.demo.retail.infrastructure.repository.model.PriceEntity;


@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Long> {

}