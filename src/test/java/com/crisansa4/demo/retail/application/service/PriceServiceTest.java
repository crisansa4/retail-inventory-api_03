package com.crisansa4.demo.retail.application.service;

import com.crisansa4.demo.retail.domain.model.Price;
import com.crisansa4.demo.retail.infrastructure.repository.PriceRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PriceServiceTest {


    @Autowired
    private PriceService priceService;
    @Autowired
    private PriceRepository priceRepository;

    @Test
    void shouldLoadDataFromDataSql() {
        List<Price> prices = priceRepository.findAll();
        assertFalse(prices.isEmpty());
    }

    @ParameterizedTest(name = "name = {0} | Date: {1}-{2}-{3} {4}:{5} | ProductId: {6} | BrandId: {7} | Expected PriceList: {8}")
    @CsvSource({
            "'Test 1', 2020,6,14,10,0, 35455, 1, 1",
            "'Test 2', 2020,6,14,16,0, 35455, 1, 2",
            "'Test 3', 2020,6,14,21,0, 35455, 1, 1",
            "'Test 4', 2020,6,15,10,0, 35455, 1, 3",
            "'Test 5', 2020,6,16,21,0, 35455, 1, 4"
    })
    void shouldReturnExpectedPriceList(String testName, int y, int m, int d, int h, int min, int productId, int brandId, int expectedPriceList) {
        LocalDateTime date = LocalDateTime.of(y, m, d, h, min);
        Price result = priceService.findApplicablePrice(date, brandId, productId);
        assertNotNull(result, testName + " failed: result was null");
        assertEquals(expectedPriceList, result.getPriceList(), testName + " failed: wrong price list");
    }
}
