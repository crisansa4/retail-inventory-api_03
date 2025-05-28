package com.crisansa4.demo.retail.infrastructure.repository.adapter;

import com.crisansa4.demo.retail.domain.Price;
import com.crisansa4.demo.retail.infrastructure.repository.PriceRepository;
import com.crisansa4.demo.retail.infrastructure.repository.model.PriceEntity;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceRepositoryAdapterTest {

    @Test
    void shouldMapEntitiesToDomain() {
        PriceRepository repo = mock(PriceRepository.class);
        PriceEntity entity = new PriceEntity();
        entity.setId(1L);
        when(repo.findAll()).thenReturn(List.of(entity));
        PriceRepositoryAdapter adapter = new PriceRepositoryAdapter(repo);
        List<Price> prices = adapter.findAll();
        assertEquals(1, prices.size());
        assertEquals(1L, prices.get(0).getId());
    }

    @Test
    void shouldFindById() {
        PriceRepository repo = mock(PriceRepository.class);
        PriceEntity entity = new PriceEntity();
        entity.setId(2L);
        when(repo.findById(2L)).thenReturn(Optional.of(entity));
        PriceRepositoryAdapter adapter = new PriceRepositoryAdapter(repo);
        assertTrue(adapter.findById(2L).isPresent());
    }

    @Test
    void shouldSaveEntity() {
        PriceRepository repo = mock(PriceRepository.class);
        PriceEntity entity = new PriceEntity();
        entity.setId(3L);
        when(repo.save(any())).thenReturn(entity);
        PriceRepositoryAdapter adapter = new PriceRepositoryAdapter(repo);
        Price price = new Price();
        price.setId(3L);
        Price saved = adapter.save(price);
        assertEquals(3L, saved.getId());
    }
}
