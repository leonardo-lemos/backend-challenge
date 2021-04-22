package dev.leonardolemos.test.validation;

import dev.leonardolemos.backendchallenge.model.Payment;
import dev.leonardolemos.backendchallenge.model.ProductOrder;
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
public class ProductOrderValidationTest {

    @Inject
    Validator validator;

    @Test
    public void supply_null_amount() {
        ProductOrder entity = new ProductOrder();
        entity.setUnits(BigDecimal.ONE);
        entity.setAmount(null);

        Set<ConstraintViolation<ProductOrder>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_negative_amount() {
        ProductOrder entity = new ProductOrder();
        entity.setUnits(BigDecimal.ONE);
        entity.setAmount(new BigDecimal(-1.0));

        Set<ConstraintViolation<ProductOrder>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_null_units() {
        ProductOrder entity = new ProductOrder();
        entity.setUnits(null);
        entity.setAmount(BigDecimal.ONE);

        Set<ConstraintViolation<ProductOrder>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_negative_units() {
        ProductOrder entity = new ProductOrder();
        entity.setUnits(new BigDecimal(-1.0));
        entity.setAmount(BigDecimal.ONE);

        Set<ConstraintViolation<ProductOrder>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_zero_units() {
        ProductOrder entity = new ProductOrder();
        entity.setUnits(BigDecimal.ZERO);
        entity.setAmount(BigDecimal.ONE);

        Set<ConstraintViolation<ProductOrder>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }
}
