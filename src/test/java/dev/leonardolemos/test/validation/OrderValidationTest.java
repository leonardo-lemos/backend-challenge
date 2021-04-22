package dev.leonardolemos.test.validation;

import dev.leonardolemos.backendchallenge.model.Order;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.validation.validator.Validator;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class OrderValidationTest {

    @Inject
    Validator validator;

    @Test
    public void supply_null_status() {
        Order entity = new Order();
        entity.setStatus(null);

        Set<ConstraintViolation<Order>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }
}
