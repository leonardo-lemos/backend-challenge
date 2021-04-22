package dev.leonardolemos.test.controller;

import dev.leonardolemos.backendchallenge.model.Manufacturer;
import dev.leonardolemos.backendchallenge.model.Order;
import dev.leonardolemos.backendchallenge.model.Product;
import dev.leonardolemos.backendchallenge.repository.ManufacturerRepository;
import io.micronaut.core.type.Argument;
import io.micronaut.data.model.Page;
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
public class ManufacturerControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    public void find_non_existing_product() {
        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(HttpRequest.GET("/manufacturers/99"));
        });

        assertNotNull(thrown.getResponse());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }

    @Test
    public void execute_crud_operations() {

        List<Long> ids = new ArrayList<>();

        Manufacturer manufacturer1 = new Manufacturer("John Doe");
        Manufacturer manufacturer2 = new Manufacturer("Adam Lopez");

        HttpRequest request = HttpRequest.POST("/manufacturers", manufacturer1);
        HttpResponse response = client.toBlocking().exchange(request);
        ids.add(entityId(response));

        assertEquals(HttpStatus.CREATED, response.getStatus());

        request = HttpRequest.POST("/manufacturers", manufacturer2);
        response = client.toBlocking().exchange(request);

        assertEquals(HttpStatus.CREATED, response.getStatus());

        Long id = entityId(response);
        ids.add(id);
        request = HttpRequest.GET("/manufacturers/" + id);

        Manufacturer manufacturer = client.toBlocking().retrieve(request, Manufacturer.class);

        assertEquals("Adam Lopez", manufacturer.getName());


        request = HttpRequest.GET("/manufacturers");
        Page manufacturerPage = client.toBlocking().retrieve(request, Page.class);
        List<Manufacturer> manufacturers = ((List<Manufacturer>) manufacturerPage.getContent());

        assertEquals(2, manufacturers.size());

        request = HttpRequest.GET("/manufacturers?size=1");
        manufacturerPage = client.toBlocking().retrieve(request, Page.class);
        manufacturers = ((List<Manufacturer>) manufacturerPage.getContent());

        assertEquals(1, manufacturers.size());
    }

    protected Long entityId(HttpResponse response) {
        String path = "/manufacturers/";
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
