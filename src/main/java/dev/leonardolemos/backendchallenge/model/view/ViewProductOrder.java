package dev.leonardolemos.backendchallenge.model.view;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Immutable
@Subselect("select * from vw_product_order")
@Table(name = "vw_product_order")
public class ViewProductOrder {

    @Id
    private Long id;

    @Column(name = "ds_name")
    private String name;

    @Column(name = "ds_units")
    private BigDecimal units;

    @Column(name = "ds_unit_price")
    private BigDecimal unitPrice;

    @Column(name = "ds_amount")
    private BigDecimal amount;

    public ViewProductOrder() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUnits() {
        return units;
    }

    public void setUnits(BigDecimal units) {
        this.units = units;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
