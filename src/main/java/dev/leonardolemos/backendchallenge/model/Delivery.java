package dev.leonardolemos.backendchallenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.leonardolemos.backendchallenge.model.enumeration.DeliveryMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_deliveries")
@SequenceGenerator(initialValue = 1, name = "defaultGenerator", sequenceName = "seq_deliveries", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "id_delivery"))
public class Delivery extends BaseEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tp_mode")
    private DeliveryMode mode;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_order", referencedColumnName = "id_order", foreignKey = @ForeignKey(name = "fk_delivery_x_order_01"))
    private Order order;

    public Delivery(DeliveryMode mode, Order order) {
        this.mode = mode;
        this.order = order;
    }

    public Delivery() {

    }

    public Delivery(DeliveryMode deliveryMode) {
    }

    public DeliveryMode getMode() {
        return mode;
    }

    public void setMode(DeliveryMode mode) {
        this.mode = mode;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    @JsonIgnore
    public long getId() {
        return super.getId();
    }

}
