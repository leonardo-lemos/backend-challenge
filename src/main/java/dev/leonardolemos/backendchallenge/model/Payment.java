package dev.leonardolemos.backendchallenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.leonardolemos.backendchallenge.model.enumeration.PaymentMode;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "tbl_payments")
@SequenceGenerator(initialValue = 1, name = "defaultGenerator", sequenceName = "seq_payments", allocationSize = 1)
@AttributeOverride(name = "id", column = @Column(name = "id_payment"))
public class Payment extends BaseEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tp_mode")
    private PaymentMode mode;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "ds_amount")
    private BigDecimal amount;

    @Min(1)
    @Column(name = "ds_installments")
    private int installments;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "ds_installment_value")
    private BigDecimal installmentValue;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_order", referencedColumnName = "id_order", foreignKey = @ForeignKey(name = "fk_payment_x_order_01"))
    private Order order;

    public Payment(PaymentMode mode, BigDecimal amount, int installments, BigDecimal installmentValue, Order order) {
        this.mode = mode;
        this.amount = amount;
        this.installments = installments;
        this.installmentValue = installmentValue;
        this.order = order;
    }

    public Payment() {

    }

    public Payment(PaymentMode mode, int installments) {
        this.mode = mode;
        this.installments = installments;
    }

    public PaymentMode getMode() {
        return mode;
    }

    public void setMode(PaymentMode mode) {
        this.mode = mode;
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

    public int getInstallments() {
        return installments;
    }

    public void setInstallments(int installments) {
        this.installments = installments;
    }

    public BigDecimal getInstallmentValue() {
        if(installmentValue != null) {
            return installmentValue.setScale(2, RoundingMode.HALF_UP);
        }

        return null;
    }

    public void setInstallmentValue(BigDecimal installmentValue) {
        this.installmentValue = installmentValue;
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
