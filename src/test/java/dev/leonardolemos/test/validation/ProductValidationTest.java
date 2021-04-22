package dev.leonardolemos.test.validation;

import dev.leonardolemos.backendchallenge.model.Product;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.validation.validator.Validator;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.Set;

import static dev.leonardolemos.test.Constants.STRING_WITH_258_CHAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public class ProductValidationTest {

    @Inject
    Validator validator;

    @Test
    public void supply_name_longer_than_128_characters() {
        Product product = new Product();
        product.setName(STRING_WITH_258_CHAR);
        product.setBarcode("1111222233334444");
        product.setUnitPrice(new BigDecimal(1.0));
        product.setDescription("Test");

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(STRING_WITH_258_CHAR.length() > 128);
        assertEquals(1, violations.size());
    }

    @Test
    public void supply_empty_name() {
        Product product = new Product();
        product.setName("");
        product.setBarcode("1111222233334444");
        product.setUnitPrice(new BigDecimal(1.0));
        product.setDescription("Test");

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_null_name() {
        Product product = new Product();
        product.setName(null);
        product.setBarcode("1111222233334444");
        product.setUnitPrice(new BigDecimal(1.0));
        product.setDescription("Test");

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_description_longer_than_128_characters() {

        Product product = new Product();
        product.setName("Grape Juice");
        product.setBarcode("1111222233334444");
        product.setUnitPrice(new BigDecimal(1.0));
        product.setDescription(STRING_WITH_258_CHAR);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(STRING_WITH_258_CHAR.length() > 256);
        assertEquals(1, violations.size());
    }

    @Test
    public void supply_empty_description() {
        Product product = new Product();
        product.setName("Grape Juice");
        product.setBarcode("1111222233334444");
        product.setUnitPrice(new BigDecimal(1.0));
        product.setDescription("");

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_null_description() {
        Product product = new Product();
        product.setName("Grape Juice");
        product.setBarcode("1111222233334444");
        product.setUnitPrice(new BigDecimal(1.0));
        product.setDescription(null);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_barcore_with_more_than_128_characters() {
        Product product = new Product();
        product.setName("Grape Juice");
        product.setBarcode(STRING_WITH_258_CHAR);
        product.setUnitPrice(new BigDecimal(1.0));
        product.setDescription("Delicious!");

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertTrue(STRING_WITH_258_CHAR.length() > 128);
        assertEquals(1, violations.size());
    }

    @Test
    public void supply_empty_barcode() {
        Product product = new Product();
        product.setName("Grape Juice");
        product.setBarcode("");
        product.setUnitPrice(new BigDecimal(1.0));
        product.setDescription("Delicious!");

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertEquals(2, violations.size());
    }

    @Test
    public void supply_null_barcode() {
        Product product = new Product();
        product.setName("Grape Juice");
        product.setBarcode(null);
        product.setUnitPrice(new BigDecimal(1.0));
        product.setDescription("Delicious");

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_null_unit_price() {
        Product product = new Product();
        product.setName("Grape Juice");
        product.setBarcode("1111222233334444");
        product.setUnitPrice(null);
        product.setDescription("Delicious");

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertEquals(1, violations.size());
    }

    @Test
    public void supply_negative_unit_price() {
        Product product = new Product();
        product.setName("Grape Juice");
        product.setBarcode("1111222233334444");
        product.setUnitPrice(new BigDecimal(-1.0));
        product.setDescription("Delicious");

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertEquals(1, violations.size());
    }
}
