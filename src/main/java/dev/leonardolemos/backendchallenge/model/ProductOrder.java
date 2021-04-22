package dev.leonardolemos.backendchallenge.model;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "crz_product_order")
@SequenceGenerator(initialValue = 1, name = "defaultGenerator", sequenceName = "seq_product_order", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "id_product_order"))
public class ProductOrder extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "id_order", referencedColumnName = "id_order", foreignKey = @ForeignKey(name = "fk_product_order_x_order_01"))
    private Order order;

    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "id_product", foreignKey = @ForeignKey(name = "fk_product_order_x_product_01"))
    private Product product;

    @NotNull
    @DecimalMin(value = "0.1", inclusive = false)
    @Column(name = "ds_units")
    private BigDecimal units;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "ds_amount")
    private BigDecimal amount;

    public ProductOrder() {
    }

    public ProductOrder(Order order, Product product, BigDecimal units, BigDecimal amount) {
        this.order = order;
        this.product = product;
        this.units = units;
        this.amount = amount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getUnits() {
        return units;
    }

    public void setUnits(BigDecimal units) {
        this.units = units;
    }
}
