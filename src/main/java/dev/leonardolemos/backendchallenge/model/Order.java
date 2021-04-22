package dev.leonardolemos.backendchallenge.model;

import dev.leonardolemos.backendchallenge.model.enumeration.OrderStatus;
import dev.leonardolemos.backendchallenge.model.view.ViewProductOrder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "tbl_orders")
@SequenceGenerator(initialValue = 1, name = "defaultGenerator", sequenceName = "seq_orders", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "id_order"))
public class Order extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "ds_status")
    private OrderStatus status;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_order", updatable = false)
    private List<ViewProductOrder> products;

    @OneToOne( mappedBy = "order")
    private Consumer consumer;

    @OneToOne(mappedBy = "order")
    private Payment payment;

    @OneToOne(mappedBy = "order")
    private Delivery delivery;

    public Order() {
    }

    public Order(OrderStatus status) {
        this.status = status;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<ViewProductOrder> getProducts() {
        return products;
    }

    public void setProducts(List<ViewProductOrder> products) {
        this.products = products;
    }
}
