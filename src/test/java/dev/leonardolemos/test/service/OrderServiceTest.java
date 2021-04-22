package dev.leonardolemos.test.service;

import dev.leonardolemos.backendchallenge.model.*;
import dev.leonardolemos.backendchallenge.model.enumeration.DeliveryMode;
import dev.leonardolemos.backendchallenge.model.enumeration.OrderStatus;
import dev.leonardolemos.backendchallenge.model.enumeration.PaymentMode;
import dev.leonardolemos.backendchallenge.model.view.ViewProductOrder;
import dev.leonardolemos.backendchallenge.repository.ManufacturerRepository;
import dev.leonardolemos.backendchallenge.repository.OrderRepository;
import dev.leonardolemos.backendchallenge.repository.ProductRepository;
import dev.leonardolemos.backendchallenge.service.OrderService;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceTest {

    @Inject
    ProductRepository productRepository;

    @Inject
    ManufacturerRepository manufacturerRepository;

    @Inject
    OrderService orderService;

    @Inject
    OrderRepository orderRepository;

    private Product product;

    @BeforeAll
    public void setup() {
        Manufacturer manufacturer = new Manufacturer("John Doe");
        manufacturer = manufacturerRepository.save(manufacturer);

        Product product = new Product("Apple", "Delicous and red", "111122223333", new BigDecimal(2.0), manufacturer);
        this.product = productRepository.save(product);
    }

    @Test
    public void save_valid_entity() {
        ViewProductOrder productInput = new ViewProductOrder();
        productInput.setUnits(new BigDecimal(2.5));
        productInput.setId(product.getId());

        List<ViewProductOrder> products = new ArrayList<>();
        products.add(productInput);

        Delivery delivery = new Delivery(DeliveryMode.INSTORE_WITHDRAW);
        Payment payment = new Payment(PaymentMode.BANKSLIP, 2);
        Consumer consumer = new Consumer("John Doe", "+554512345678", "some@one.com");

        assertNotNull(orderService);
        Order order = orderService.saveOrder(products, consumer, payment, delivery);

        assertNotNull(order);
        assertEquals(OrderStatus.PENDING_CONFIRMATION, order.getStatus());
        assertEquals(true, order.getId() > 0);

        assertNotNull(order.getProducts());
        assertEquals(1, order.getProducts().size());
        assertEquals(new BigDecimal(5.00).setScale(2, RoundingMode.HALF_UP), order.getProducts().get(0).getAmount());

        assertNotNull(order.getConsumer());
        assertNotNull(order.getDelivery());

        assertNotNull(order.getPayment());
        assertEquals(new BigDecimal(5.00).setScale(2, RoundingMode.HALF_UP), order.getPayment().getAmount());
        assertEquals(new BigDecimal(2.50).setScale(2, RoundingMode.HALF_UP), order.getPayment().getInstallmentValue());
    }

    @Test
    public void confirm_cancelled_order() {
        Order order = orderRepository.save(new Order(OrderStatus.CANCELLED));

        assertEquals(true, order.getId() > 0);
        assertEquals(OrderStatus.CANCELLED, order.getStatus());

        orderService.confirmOrder(order.getId());

        order = orderRepository.findById(order.getId()).orElse(null);
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }
}
