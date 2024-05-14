package com.codeconverse.GuavaExample.fakedata;

import com.codeconverse.GuavaExample.model.Product;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that provides fake data for product recommendations.
 */
public class FakeData {
    private static final Map<String, List<Product>> recommendations = new HashMap<>();

    static {
        recommendations.putAll(Map.of(
                "user1", List.of(new Product("Laptop"), new Product("Mouse")),
                "user2", List.of(new Product("Smartphone"), new Product("Earphones")),
                "user3", List.of(new Product("Book"), new Product("Bookmark"))
        ));
    }

    /**
     * Gets product recommendations for a given user ID.
     * Simulates a delay to mimic data fetching.
     *
     * @param userId the ID of the user
     * @return a list of recommended products for the user
     */

    public static List<Product> getRecommendations(String userId) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Exception occurred");
        }
        return recommendations.getOrDefault(userId, Collections.emptyList());
    }

}
