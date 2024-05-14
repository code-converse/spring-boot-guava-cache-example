
# Spring Boot Guava Cache Example

This project demonstrates how to integrate Guava Cache into a Spring Boot application for caching product recommendations. The application includes a REST API to fetch product recommendations, with caching implemented to improve performance.

## Project Structure

```
src/
└── main/
    ├── java/
    │   └── com/
    │       └── codeconverse/
    │           └── GuavaExample/
    │               ├── config/
    │               │   └── CacheConfig.java
    │               ├── controller/
    │               │   └── ProductController.java
    │               ├── fakedata/
    │               │   └── FakeData.java
    │               ├── model/
    │               │   └── Product.java
    │               └── GuavaExampleApplication.java
    └── resources/
        └── application.properties
```

## Class Descriptions

### 1. `GuavaExampleApplication.java`
This is the main entry point of the Spring Boot application. It contains the `main` method which bootstraps the application.
```java
@SpringBootApplication
public class GuavaExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(GuavaExampleApplication.class, args);
    }
}
```

### 2. `CacheConfig.java`
This configuration class sets up the Guava cache. Declaring the cache as a bean allows Spring to manage its lifecycle and inject it where needed.
```java
@Configuration
public class CacheConfig {
    @Bean
    public Cache<String, List<Product>> productCache() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES)
                .maximumSize(4)
                .build();
    }
}
```
**Why declare the cache as a bean?**  
Declaring the cache as a bean allows Spring to manage its lifecycle, making it available for dependency injection. This promotes reusability and ensures that the cache configuration is centralized.

### 3. `ProductController.java`
This REST controller handles product-related requests. It fetches product recommendations and utilizes the cache to improve performance.
```java
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    @Qualifier("productCache")
    private Cache<String, List<Product>> cache;

    @GetMapping("/recommendations")
    @SneakyThrows
    public List<Product> getProducts(@RequestParam String userId) {
        return cache.get(userId, () -> FakeData.getRecommendations(userId));
    }
}
```

### 4. `FakeData.java`
This class simulates product recommendation data. It mimics data fetching with a delay to demonstrate caching benefits.
```java
public class FakeData {
    private static final Map<String, List<Product>> recommendations = new HashMap<>();

    static {
        recommendations.putAll(Map.of(
                "user1", List.of(new Product("Laptop"), new Product("Mouse")),
                "user2", List.of(new Product("Smartphone"), new Product("Earphones")),
                "user3", List.of(new Product("Book"), new Product("Bookmark"))
        ));
    }

    public static List<Product> getRecommendations(String userId) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Exception occurred");
        }
        return recommendations.getOrDefault(userId, Collections.emptyList());
    }

    public static void add(String userId) {
        recommendations.put(userId, List.of(new Product("Smartphone"), new Product("Earphones")));
    }
}
```

### 5. `Product.java`
This model class represents a product entity.
```java
@Getter
@AllArgsConstructor
@Setter
public class Product {
    private String name;
}
```

## Step-by-Step Guide to Setting Up Guava Cache in Spring Boot

1. **Create a Spring Boot Project:**
   Use [Spring Initializr](https://start.spring.io) to bootstrap your project with the necessary dependencies (Spring Web).

2. **Add Guava Dependency:**
   Add the Guava dependency to your `pom.xml`.
   ```xml
   <dependency>
       <groupId>com.google.guava</groupId>
       <artifactId>guava</artifactId>
       <version>33.2.0-jre</version>
   </dependency>
   ```

3. **Configure Guava Cache:**
   Create a configuration class (`CacheConfig.java`) to set up the cache.
   ```java
   @Configuration
   public class CacheConfig {
       @Bean
       public Cache<String, List<Product>> productCache() {
           return CacheBuilder.newBuilder()
                   .expireAfterWrite(2, TimeUnit.MINUTES)
                   .maximumSize(4)
                   .build();
       }
   }
   ```

4. **Implement REST Controller:**
   Create a controller (`ProductController.java`) to handle requests and use the cache.
   ```java
   @RestController
   @RequestMapping("/products")
   public class ProductController {
       @Autowired
       @Qualifier("productCache")
       private Cache<String, List<Product>> cache;

       @GetMapping("/recommendations")
       @SneakyThrows
       public List<Product> getProducts(@RequestParam String userId) {
           return cache.get(userId, () -> FakeData.getRecommendations(userId));
       }
   }
   ```

5. **Simulate Data:**
   Create a class (`FakeData.java`) to provide simulated data for testing.
   ```java
   public class FakeData {
       // Implementations as shown above
   }
   ```

6. **Run the Application:**
   Run the `GuavaExampleApplication` class and test the endpoint using a tool like Postman or your browser.

## Conclusion
This project provides a simple yet effective demonstration of using Guava Cache in a Spring Boot application. By following the steps outlined, you can set up caching to improve the performance of your Spring Boot applications.