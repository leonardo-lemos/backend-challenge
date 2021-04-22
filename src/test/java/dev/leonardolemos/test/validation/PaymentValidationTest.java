package dev.leonardolemos.test.validation;

import dev.leonardolemos.backendchallenge.model.Payment;
import dev.leonardolemos.backendchallenge.model.enumeration.PaymentMode;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.validation.validator.Validator;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class PaymentValidationTest {

    @Inject
    Validator validator;

    @Test
    public void supply_null_mode() {
        Payment entity = new Payment();
        entity.setInstallments(1);
        entity.setAmount(BigDecimal.ONE);
        entity.setInstallmentValue(BigDecimal.ONE);
        entity.setMode(null);

        Set<ConstraintViolation<Payment>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_null_amount() {
        Payment entity = new Payment();
        entity.setInstallments(1);
        entity.setAmount(null);
        entity.setInstallmentValue(BigDecimal.ONE);
        entity.setMode(PaymentMode.BANKSLIP);

        Set<ConstraintViolation<Payment>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_negative_amount() {
        Payment entity = new Payment();
        entity.setInstallments(1);
        entity.setAmount(new BigDecimal(-1.0));
        entity.setInstallmentValue(BigDecimal.ONE);
        entity.setMode(PaymentMode.BANKSLIP);

        Set<ConstraintViolation<Payment>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_zero_installment() {
        Payment entity = new Payment();
        entity.setInstallments(0);
        entity.setInstallmentValue(BigDecimal.ONE);
        entity.setAmount(BigDecimal.ONE);
        entity.setMode(PaymentMode.BANKSLIP);

        Set<ConstraintViolation<Payment>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_negative_installment() {
        Payment entity = new Payment();
        entity.setInstallments(-1);
        entity.setInstallmentValue(BigDecimal.ONE);
        entity.setAmount(BigDecimal.ONE);
        entity.setMode(PaymentMode.BANKSLIP);

        Set<ConstraintViolation<Payment>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_null_installment_value() {
        Payment entity = new Payment();
        entity.setInstallments(1);
        entity.setInstallmentValue(null);
        entity.setAmount(BigDecimal.ONE);
        entity.setMode(PaymentMode.BANKSLIP);

        Set<ConstraintViolation<Payment>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_installment_value() {
        Payment entity = new Payment();
        entity.setInstallments(1);
        entity.setInstallmentValue(new BigDecimal(-1.0));
        entity.setAmount(BigDecimal.ONE);
        entity.setMode(PaymentMode.BANKSLIP);

        Set<ConstraintViolation<Payment>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }
}
