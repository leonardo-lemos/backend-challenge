package dev.leonardolemos.backendchallenge.model.view;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Immutable
@Subselect("select * from vw_product_order")
@Table(name = "vw_product_order")
public class ViewProductOrder {

    @Id
    private long id;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getUnitPrice() {
        if(unitPrice != null) {
            return unitPrice.setScale(2, RoundingMode.HALF_UP);
        }

        return null;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmount() {
        if(amount != null) {
            return amount.setScale(2, RoundingMode.HALF_UP);
        }

        return null;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
