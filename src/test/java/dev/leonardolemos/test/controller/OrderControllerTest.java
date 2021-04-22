package dev.leonardolemos.test.controller;

import dev.leonardolemos.backendchallenge.model.*;
import dev.leonardolemos.backendchallenge.model.enumeration.DeliveryMode;
import dev.leonardolemos.backendchallenge.model.enumeration.OrderStatus;
import dev.leonardolemos.backendchallenge.model.enumeration.PaymentMode;
import dev.leonardolemos.backendchallenge.model.view.ViewProductOrder;
import dev.leonardolemos.backendchallenge.repository.ManufacturerRepository;
import dev.leonardolemos.backendchallenge.repository.ProductRepository;
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
public class OrderControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    ManufacturerRepository manufacturerRepository;

    @Inject
    ProductRepository productRepository;

    private Product product1;
    private Product product2;


    @BeforeAll
    public void setup() {
        Manufacturer manufacturer = new Manufacturer("John Doe");
        manufacturer = manufacturerRepository.save(manufacturer);

        Product grapeJuice = new Product("Grape Juice", "Delicious and fresh Grape Juice", "1111111111111111", new BigDecimal(1.0), manufacturer);
        Product appleJuice = new Product("Apple Juice", "Delicious and fresh Apple Juice", "2222222222222222", new BigDecimal(1.0), manufacturer);

        product1 = productRepository.save(grapeJuice);
        product2 = productRepository.save(appleJuice);
    }

    @Test
    public void find_non_existing_order() {
        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(HttpRequest.GET("/orders/99"));
        });

        assertNotNull(thrown.getResponse());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }

    @Test
    public void execute_crud_operations() {

        List<Long> ids = new ArrayList<>();
        List<ViewProductOrder> productList = new ArrayList<>();

        ViewProductOrder vwProduct1 = new ViewProductOrder();
        vwProduct1.setId(product1.getId());
        vwProduct1.setUnits(BigDecimal.ONE);

        ViewProductOrder vwProduct2 = new ViewProductOrder();
        vwProduct2.setId(product2.getId());
        vwProduct2.setUnits(BigDecimal.ONE);

        productList.add(vwProduct1);
        productList.add(vwProduct2);


        Order order1 = new Order();
        order1.setPayment(new Payment(PaymentMode.BANKSLIP, 1));
        order1.setDelivery(new Delivery(DeliveryMode.INSTORE_WITHDRAW));
        order1.setStatus(OrderStatus.PENDING_CONFIRMATION);
        order1.setConsumer(new Consumer("John Doe", "+5527998764561", "jhon@doe.com"));
        order1.setProducts(productList);

        Order order2 = new Order();
        order2.setPayment(new Payment(PaymentMode.BANKSLIP, 2));
        order2.setDelivery(new Delivery(DeliveryMode.INSTORE_WITHDRAW));
        order2.setStatus(OrderStatus.PENDING_CONFIRMATION);
        order2.setConsumer(new Consumer("John Doe", "+5527998764561", "jhon@doe.com"));
        order2.setProducts(productList);

        HttpRequest request = HttpRequest.POST("/orders", order1);
        HttpResponse response = client.toBlocking().exchange(request);
        ids.add(entityId(response));

        assertEquals(HttpStatus.CREATED, response.getStatus());

        request = HttpRequest.POST("/orders", order2);
        response = client.toBlocking().exchange(request);

        assertEquals(HttpStatus.CREATED, response.getStatus());

        Long id = entityId(response);
        ids.add(id);
        request = HttpRequest.GET("/orders/" + id);

        Order order = client.toBlocking().retrieve(request, Order.class);

        assertEquals(2, order.getId());


        request = HttpRequest.PUT("/orders/" + id + "/confirm", new Order());
        response = client.toBlocking().exchange(request);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());

        request = HttpRequest.GET("/orders/" + id);
        order = client.toBlocking().retrieve(request, Order.class);
        assertEquals(OrderStatus.CONFIRMED, order.getStatus());


        request = HttpRequest.PUT("/orders/" + id + "/cancel", new Order());
        response = client.toBlocking().exchange(request);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());

        request = HttpRequest.GET("/orders/" + id);
        order = client.toBlocking().retrieve(request, Order.class);
        assertEquals(OrderStatus.CANCELLED, order.getStatus());

        request = HttpRequest.GET("/orders");
        Page orderPage = client.toBlocking().retrieve(request, Page.class);
        List<Order> orders = ((List<Order>) orderPage.getContent());

        assertEquals(2, orders.size());

        request = HttpRequest.GET("/orders?size=1");
        orderPage = client.toBlocking().retrieve(request, Page.class);
        orders = ((List<Order>) orderPage.getContent());

        assertEquals(1, orders.size());
    }

    protected Long entityId(HttpResponse response) {
        String path = "/orders/";
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
