package com.codeconverse.GuavaExample.controller;

import com.codeconverse.GuavaExample.fakedata.FakeData;
import com.codeconverse.GuavaExample.model.Product;
import com.google.common.cache.Cache;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for handling product-related requests.
 */
@RestController
@RequestMapping("/products")
public class ProductController {


    @Autowired
    @Qualifier("productCache")
    private Cache<String, List<Product>> cache;

    /**
     * Gets product recommendations for a given user ID.
     * The recommendations are fetched from the cache if available,
     * otherwise they are generated using fake data and then cached.
     *
     * @param userId the ID of the user
     * @return a list of recommended products for the user
     */
    @GetMapping("/recommendations")
    @SneakyThrows
    public List<Product> getProducts(@RequestParam String userId) {
        return cache.get(userId, () -> FakeData.getRecommendations(userId));
    }
}
