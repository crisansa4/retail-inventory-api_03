package com.crisansa4.demo.retail.infrastructure.repository.adapter;

import com.crisansa4.demo.retail.domain.Price;
import com.crisansa4.demo.retail.application.port.PricePort;
import com.crisansa4.demo.retail.infrastructure.repository.PriceRepository;
import com.crisansa4.demo.retail.infrastructure.repository.mapper.PriceMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PriceRepositoryAdapter implements PricePort {

    private final PriceRepository priceRepository;

    public PriceRepositoryAdapter(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public List<Price> findAll() {
        return priceRepository.findAll().stream()
                .map(PriceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Price> findById(Long id) {
        return priceRepository.findById(id).map(PriceMapper::toDomain);
    }

    @Override
    public Price save(Price price) {
        return PriceMapper.toDomain(
                priceRepository.save(PriceMapper.toEntity(price))
        );
    }

    @Override
    public List<Price> findByBrandIdAndProductIdAndDate(int brandId, int productId, LocalDateTime date) {
        return priceRepository
                .findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(brandId, productId, date, date)
                .stream()
                .map(PriceMapper::toDomain)
                .toList();
                //collect(Collectors.toList());
    }
}
