package dev.leonardolemos.test.controller;

import dev.leonardolemos.backendchallenge.model.Manufacturer;
import dev.leonardolemos.backendchallenge.model.Product;
import dev.leonardolemos.backendchallenge.repository.ManufacturerRepository;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    ManufacturerRepository manufacturerRepository;

    private Manufacturer manufacturer;

    @BeforeAll
    public void setup() {
        Manufacturer manufacturer = new Manufacturer("John Doe");
        manufacturerRepository.save(manufacturer);
        this.manufacturer = manufacturerRepository.findByName("John Doe").orElse(null);
    }

    @Test
    public void find_non_existing_product() {
        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(HttpRequest.GET("/products/99"));
        });

        assertNotNull(thrown.getResponse());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }

    @Test
    public void execute_crud_operations() {

        List<Long> ids = new ArrayList<>();

        Product grapeJuice = new Product("Grape Juice", "Delicious and fresh Grape Juice", "1111111111111111", new BigDecimal(1.0), manufacturer);
        Product appleJuice = new Product("Apple Juice", "Delicious and fresh Apple Juice", "2222222222222222", new BigDecimal(1.0), manufacturer);

        HttpRequest request = HttpRequest.POST("/products", grapeJuice);
        HttpResponse response = client.toBlocking().exchange(request);
        ids.add(entityId(response));

        assertEquals(HttpStatus.CREATED, response.getStatus());

        request = HttpRequest.POST("/products", appleJuice);
        response = client.toBlocking().exchange(request);

        assertEquals(HttpStatus.CREATED, response.getStatus());

        Long id = entityId(response);
        ids.add(id);
        request = HttpRequest.GET("/products/" + id);

        Product product = client.toBlocking().retrieve(request, Product.class);

        assertEquals("Apple Juice", product.getName());

        product.setName("Mango Juice");

        request = HttpRequest.PUT("/products", product);
        response = client.toBlocking().exchange(request);

        assertEquals(HttpStatus.OK, response.getStatus());

        request = HttpRequest.GET("/products/" + id);
        product = client.toBlocking().retrieve(request, Product.class);
        assertEquals("Mango Juice", product.getName());

        request = HttpRequest.GET("/products");
        List<Product> products = client.toBlocking().retrieve(request, Argument.of(List.class, Product.class));

        assertEquals(2, products.size());

        request = HttpRequest.GET("/products?size=1");
        products = client.toBlocking().retrieve(request, Argument.of(List.class, Product.class));

        assertEquals(1, products.size());
        assertEquals("Grape Juice", products.get(0).getName());

        request = HttpRequest.GET("/products?size=1&sort=name,DESC");
        products = client.toBlocking().retrieve(request, Argument.of(List.class, Product.class));

        assertEquals(1, products.size());
        assertEquals("Mango Juice", products.get(0).getName());

        // cleanup:
        for (Long productId : ids) {
            request = HttpRequest.DELETE("/products/" + productId);
            response = client.toBlocking().exchange(request);
            assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
        }
    }

    protected Long entityId(HttpResponse response) {
        String path = "/products/";
        String value = response.header(HttpHeaders.LOCATION);
        if (value == null) {
            return null;
        }
        int index = value.indexOf(path);
        if (index != -1) {
            return Long.valueOf(value.substring(index + path.length()));
        }
        return null;
    }
}
