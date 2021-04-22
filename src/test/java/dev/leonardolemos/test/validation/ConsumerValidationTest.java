package dev.leonardolemos.test.validation;

import dev.leonardolemos.backendchallenge.model.Consumer;
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
public class ConsumerValidationTest {

    @Inject
    Validator validator;

    @Test
    public void supply_name_longer_than_128_characters() {
        Consumer entity = new Consumer();
        entity.setName(STRING_WITH_258_CHAR);
        entity.setEmail("jhon@doe.com");
        entity.setPhone("+5527971654151");

        Set<ConstraintViolation<Consumer>> violations = validator.validate(entity);

        assertTrue(STRING_WITH_258_CHAR.length() > 128);
        assertEquals(1, violations.size());
    }

    @Test
    public void supply_empty_name() {
        Consumer entity = new Consumer();
        entity.setName("");
        entity.setEmail("jhon@doe.com");
        entity.setPhone("+5527971654151");

        Set<ConstraintViolation<Consumer>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }
    @Test
    public void supply_null_name() {
        Consumer entity = new Consumer();
        entity.setName(null);
        entity.setEmail("jhon@doe.com");
        entity.setPhone("+5527971654151");

        Set<ConstraintViolation<Consumer>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_email_longer_than_128_characters() {
        Consumer entity = new Consumer();
        entity.setName("John Doe");
        entity.setEmail(STRING_WITH_258_CHAR + "@test.com");
        entity.setPhone("+5527971654151");

        Set<ConstraintViolation<Consumer>> violations = validator.validate(entity);

        assertTrue(STRING_WITH_258_CHAR.length() > 254);
        assertEquals(2, violations.size());
    }

    @Test
    public void supply_empty_email() {
        Consumer entity = new Consumer();
        entity.setName("John Doe");
        entity.setEmail("");
        entity.setPhone("+5527971654151");

        Set<ConstraintViolation<Consumer>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_null_email() {
        Consumer entity = new Consumer();
        entity.setName("John Doe");
        entity.setEmail(null);
        entity.setPhone("+5527971654151");

        Set<ConstraintViolation<Consumer>> violations = validator.validate(entity);

        assertEquals(2, violations.size());
    }

    @Test
    public void supply_phone_short_than_13_characters() {
        Consumer entity = new Consumer();
        entity.setName("John Doe");
        entity.setEmail("jhon@doe.com");
        entity.setPhone("");

        Set<ConstraintViolation<Consumer>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_phone_longer_than_14_characters() {
        Consumer entity = new Consumer();
        entity.setName("John Doe");
        entity.setEmail("jhon@doe.com");
        entity.setPhone("123456789012345");

        Set<ConstraintViolation<Consumer>> violations = validator.validate(entity);

        assertEquals(1, violations.size());
    }
}
