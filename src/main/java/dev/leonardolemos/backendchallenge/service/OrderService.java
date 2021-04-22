package dev.leonardolemos.backendchallenge.service;


import dev.leonardolemos.backendchallenge.model.*;
import dev.leonardolemos.backendchallenge.model.enumeration.OrderStatus;
import dev.leonardolemos.backendchallenge.model.view.ViewProductOrder;
import dev.leonardolemos.backendchallenge.repository.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class OrderService {

    @Inject
    OrderRepository orderRepository;
    @Inject
    ProductRepository productRepository;
    @Inject
    ProductOrderRepository productOrderRepository;
    @Inject
    ConsumerRepository consumerRepository;
    @Inject
    PaymentRepository paymentRepository;
    @Inject
    DeliveryRepository deliveryRepository;

    @Transactional
    public Order saveOrder(@NotEmpty List<ViewProductOrder> orderedProducts, Consumer consumer, Payment payment, Delivery delivery) {
        List<ProductOrder> productOrders = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (ViewProductOrder op : orderedProducts) {
            Product product = productRepository.findById(op.getId()).orElse(null);

            if (product == null) {
                continue;
            }

            BigDecimal productAmount = op.getUnits().multiply(product.getUnitPrice());
            totalAmount = totalAmount.add(productAmount);

            ProductOrder productOrder = new ProductOrder();
            productOrder.setProduct(product);
            productOrder.setAmount(productAmount);
            productOrder.setUnits(op.getUnits());

            op.setAmount(productAmount);
            op.setUnitPrice(product.getUnitPrice());
            op.setName(product.getName());

            productOrders.add(productOrder);
        }

        BigDecimal installmentValue = totalAmount.divide(new BigDecimal(payment.getInstallments()));

        Order order = new Order(OrderStatus.PENDING_CONFIRMATION);
        order = orderRepository.save(order);

        payment.setAmount(totalAmount);
        payment.setInstallmentValue(installmentValue);

        payment.setOrder(order);
        delivery.setOrder(order);
        consumer.setOrder(order);

        payment = paymentRepository.save(payment);
        delivery = deliveryRepository.save(delivery);
        consumer = consumerRepository.save(consumer);

        order.setConsumer(consumer);
        order.setDelivery(delivery);
        order.setPayment(payment);
        order.setProducts(orderedProducts);

        for (ProductOrder po : productOrders) {
            po.setOrder(order);

            productOrderRepository.save(po);
        }

        return order;
    }

    public void confirmOrder(long id) {
        OrderStatus orderStatus = orderRepository.findStatusById(id).orElse(null);

        if (orderStatus != null && orderStatus != OrderStatus.CANCELLED) {
            orderRepository.update(id, OrderStatus.CONFIRMED);
        }
    }
}
