package dev.leonardolemos.test.controller;

import dev.leonardolemos.backendchallenge.model.Manufacturer;
import dev.leonardolemos.backendchallenge.model.Product;
import dev.leonardolemos.backendchallenge.repository.ManufacturerRepository;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class ProductControllerTest {

    @Inject
    @Client("/products")
    HttpClient client;

    @Inject
    ManufacturerRepository manufacturerRepository;

    private Manufacturer manufacturer;

    @BeforeAll
    public void setup() {
        Manufacturer manufacturer = new Manufacturer("John Doe");
        this.manufacturer = manufacturerRepository.save(manufacturer);
    }

    @Test
    public void supply_name_longer_than_128_characters() {
        Product Product = new Product("ajk3c9okahmkQu93qUy3mCUTDJzF6C4U2OJAsN0Kqkp7cEuyRORbhdBiD4" +
                "JMGLTeCkGDOWX6IXVbZOiw6D2RYvzoAcNF3Vr1oPQJJhCXYy4b1QL3g0AzNHe67jS9E6Vs2", "Just a Product", "1111222233334444", manufacturer);

        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(HttpRequest.POST("/", manufacturer));
        });

        assertNotNull(thrown.getResponse());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
    }

    @Test
    public void supply_empty_name() {
        Manufacturer manufacturer = new Manufacturer("");

        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(HttpRequest.POST("/", manufacturer));
        });

        assertNotNull(thrown.getResponse());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
    }
}
