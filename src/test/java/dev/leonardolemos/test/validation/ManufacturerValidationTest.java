package dev.leonardolemos.test.validation;

import dev.leonardolemos.backendchallenge.model.Consumer;
import dev.leonardolemos.backendchallenge.model.Delivery;
import dev.leonardolemos.backendchallenge.model.Manufacturer;
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
public class ManufacturerValidationTest {

    @Inject
    Validator validator;

    @Test
    public void supply_name_longer_than_128_characters() {
        Manufacturer entity = new Manufacturer();
        entity.setName(STRING_WITH_258_CHAR);

        Set<ConstraintViolation<Manufacturer>> violations = validator.validate(entity);

        assertTrue(STRING_WITH_258_CHAR.length() > 128);
        assertEquals(1, violations.size());
    }

    @Test
    public void supply_empty_name() {
        Manufacturer entity = new Manufacturer();
        entity.setName("");

        Set<ConstraintViolation<Manufacturer>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_null_name() {
        Manufacturer entity = new Manufacturer();
        entity.setName(null);

        Set<ConstraintViolation<Manufacturer>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }
}
