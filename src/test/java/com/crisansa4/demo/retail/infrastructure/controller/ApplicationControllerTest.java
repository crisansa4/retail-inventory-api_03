package com.crisansa4.demo.retail.infrastructure.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test 1: 14-06-2020 10:00, product 35455, brand 1")
    void applicablePrice_case1() throws Exception {
        mockMvc.perform(get("/api/applicablePrice")
                .param("dateTime", "2020-06-14T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.priceList", is(1)));
    }

    @Test
    @DisplayName("Test 2: 14-06-2020 16:00, product 35455, brand 1")
    void applicablePrice_case2() throws Exception {
        mockMvc.perform(get("/api/applicablePrice")
                .param("dateTime", "2020-06-14T16:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.priceList", is(2)));
    }

    @Test
    @DisplayName("Test 3: 14-06-2020 21:00, product 35455, brand 1")
    void applicablePrice_case3() throws Exception {
        mockMvc.perform(get("/api/applicablePrice")
                .param("dateTime", "2020-06-14T21:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.priceList", is(1)));
    }

    @Test
    @DisplayName("Test 4: 15-06-2020 10:00, product 35455, brand 1")
    void applicablePrice_case4() throws Exception {
        mockMvc.perform(get("/api/applicablePrice")
                .param("dateTime", "2020-06-15T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.priceList", is(3)));
    }

    @Test
    @DisplayName("Test 5: 16-06-2020 21:00, product 35455, brand 1")
    void applicablePrice_case5() throws Exception {
        mockMvc.perform(get("/api/applicablePrice")
                .param("dateTime", "2020-06-16T21:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.priceList", is(4)));
    }

    @Test
    @DisplayName("404 when no applicable price")
    void applicablePrice_notFound() throws Exception {
        mockMvc.perform(get("/api/applicablePrice")
                .param("dateTime", "2025-01-01T00:00:00")
                .param("productId", "99999")
                .param("brandId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("Not found")));
    }

    @Test
    @DisplayName("400 when parameter is badly formatted")
    void applicablePrice_badRequest_format() throws Exception {
        mockMvc.perform(get("/api/applicablePrice")
                .param("dateTime", "bad-date-format")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Bad request")));
    }

    @Test
    @DisplayName("400 when parameter is missing")
    void applicablePrice_badRequest_missingParam() throws Exception {
        mockMvc.perform(get("/api/applicablePrice")
                .param("dateTime", "2020-06-14T10:00:00")
                .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Bad request")));
    }

    @Test
    @DisplayName("GET /api/prices returns all prices")
    void getAllPrices_returnsAll() throws Exception {
        mockMvc.perform(get("/api/prices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$[0].productId", notNullValue()));
    }

    @Test
    @DisplayName("GET /api/prices/{id} returns price if exists")
    void getPriceById_returnsPrice() throws Exception {
        mockMvc.perform(get("/api/prices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @DisplayName("GET /api/prices/{id} returns 404 if not found")
    void getPriceById_returns404() throws Exception {
        mockMvc.perform(get("/api/prices/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/prices creates a new price")
    void createPrice_returnsCreated() throws Exception {
        String newPriceJson = """
            {
                "brandId": 1,
                "startDate": "2023-01-01T00:00:00",
                "endDate": "2023-12-31T23:59:59",
                "priceList": 5,
                "productId": 12345,
                "priority": 0,
                "price": 99.99,
                "curr": "EUR"
            }
            """;
        mockMvc.perform(post("/api/prices")
                .contentType("application/json")
                .content(newPriceJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId", is(12345)))
                .andExpect(jsonPath("$.priceList", is(5)));
    }
}
