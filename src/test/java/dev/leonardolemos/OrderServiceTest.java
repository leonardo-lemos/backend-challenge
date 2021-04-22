//package dev.leonardolemos;
//
//import dev.leonardolemos.backendchallenge.model.Delivery;
//import dev.leonardolemos.backendchallenge.model.Payment;
//import dev.leonardolemos.backendchallenge.model.Product;
//import dev.leonardolemos.backendchallenge.model.enumeration.DeliveryMode;
//import dev.leonardolemos.backendchallenge.model.enumeration.PaymentMode;
//import dev.leonardolemos.backendchallenge.model.view.ViewProductOrder;
//import dev.leonardolemos.backendchallenge.repository.ProductRepository;
//import dev.leonardolemos.backendchallenge.service.OrderService;
//import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//
//import javax.inject.Inject;
//import java.util.ArrayList;
//import java.util.List;
//
//@MicronautTest
//public class OrderServiceTest {
//    @Inject
//    OrderService orderService;
//    @Inject
//    ProductRepository productRepository;
//    @Inject
//    dev.leonardolemos.backendchallenge.repository.ManufacturerRepository manufacturerRepository;
//
//    private dev.leonardolemos.backendchallenge.model.Order order;
//    private Product product;
//
//    @BeforeAll
//    public void setupTest() {
//        dev.leonardolemos.backendchallenge.model.Manufacturer manufacturer = new dev.leonardolemos.backendchallenge.model.Manufacturer("John Doe");
//        manufacturer = manufacturerRepository.save(manufacturer);
//
//        Product product = new Product("Apple", "Delicous and red", "111122223333", manufacturer);
//        this.product = productRepository.save(product);
//    }
//
//    @Test
//    @DisplayName("Save Order with valid parameters")
//    public void saveOrderWithValidParameters() {
//        ViewProductOrder product = new ViewProductOrder();
//        product.setAmount(2.5);
//        product.setId(1L);
//
//        List<ViewProductOrder> products = new ArrayList<>();
//        products.add(product);
//
//        Delivery delivery = new Delivery(DeliveryMode.INSTORE_WITHDRAW);
//        Payment payment = new Payment(PaymentMode.BANKSLIP, 3);
//        dev.leonardolemos.backendchallenge.model.Consumer consumer = new dev.leonardolemos.backendchallenge.model.Consumer("John Doe", "+554512345678", "some@one.com");
//
//        Assert.assertNotNull(orderService);
////        dev.leonardolemos.backendchallenge.model.Order order = orderService.saveOrder(products, consumer, payment, delivery);
//    }
//}
