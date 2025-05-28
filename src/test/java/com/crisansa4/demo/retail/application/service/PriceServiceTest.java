package com.crisansa4.demo.retail.application.service;

import com.crisansa4.demo.retail.domain.Price;
import com.crisansa4.demo.retail.application.port.PricePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceServiceTest {

    private PricePort pricePort;
    private PriceService priceService;

    @BeforeEach
    void setup() {
        pricePort = mock(PricePort.class);
        priceService = new PriceService(pricePort);
    }

    @Test
    void getAllPrices_returnsList() {
        List<Price> prices = List.of(new Price());
        when(pricePort.findAll()).thenReturn(prices);
        assertEquals(prices, priceService.getAllPrices());
    }

    @Test
    void getPriceById_returnsPrice() {
        Price price = new Price();
        when(pricePort.findById(1L)).thenReturn(Optional.of(price));
        assertEquals(price, priceService.getPriceById(1L));
    }

    @Test
    void getPriceById_returnsNullIfNotFound() {
        when(pricePort.findById(1L)).thenReturn(Optional.empty());
        assertNull(priceService.getPriceById(1L));
    }

    @Test
    void createPrice_returnsSavedPrice() {
        Price price = new Price();
        when(pricePort.save(price)).thenReturn(price);
        assertEquals(price, priceService.createPrice(price));
    }

    @Test
    void shouldReturnNullWhenNoApplicablePrice() {
        when(pricePort.findByBrandIdAndProductIdAndDate(anyInt(), anyInt(), any())).thenReturn(List.of());
        Price result = priceService.findApplicablePrice(LocalDateTime.now(), 1, 1);
        assertNull(result);
    }

    @Test
    void shouldCreatePrice() {
        Price price = new Price();
        when(pricePort.save(price)).thenReturn(price);
        assertEquals(price, priceService.createPrice(price));
    }

    @Test
    void shouldReturnPriceByIdIfExists() {
        Price price = new Price();
        when(pricePort.findById(1L)).thenReturn(Optional.of(price));
        assertEquals(price, priceService.getPriceById(1L));
    }

    @Test
    void shouldReturnNullIfPriceByIdNotExists() {
        when(pricePort.findById(1L)).thenReturn(Optional.empty());
        assertNull(priceService.getPriceById(1L));
    }

    @Test
    void findApplicablePrice_returnsPriceWithHighestPriority() {
        Price low = new Price();
        low.setPriority(1);
        Price high = new Price();
        high.setPriority(10);
        when(pricePort.findByBrandIdAndProductIdAndDate(anyInt(), anyInt(), any()))
                .thenReturn(List.of(low, high));
        Price result = priceService.findApplicablePrice(LocalDateTime.now(), 1, 1);
        assertEquals(high, result);
    }
}
