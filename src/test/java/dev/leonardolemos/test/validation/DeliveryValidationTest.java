package dev.leonardolemos.test.validation;

import dev.leonardolemos.backendchallenge.model.Consumer;
import dev.leonardolemos.backendchallenge.model.Delivery;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.validation.validator.Validator;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import java.util.Set;

import static dev.leonardolemos.test.Constants.STRING_WITH_258_CHAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public class DeliveryValidationTest {

    @Inject
    Validator validator;

    @Test
    public void supply_null_delivery_mode() {
        Delivery entity = new Delivery();
        entity.setMode(null);

        Set<ConstraintViolation<Delivery>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }
}
