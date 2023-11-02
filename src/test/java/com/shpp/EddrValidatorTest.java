package com.shpp;

import com.shpp.eddr.EddrValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;



import static org.junit.jupiter.api.Assertions.*;

public class EddrValidatorTest {
    private EddrValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        validator = new EddrValidator();
        context = Mockito.mock(ConstraintValidatorContext.class);
    }

    @Test
    public void testValidEddr() {
        String validEddr = "1234567890123";
        assertTrue(validator.isValid(validEddr, context));
    }

    @Test
    public void testIncorrectEddr() {
        String invalidEddr = "1234567890124";
        assertFalse(validator.isValid(invalidEddr, context));
    }

    @Test
    public void testNullEddr() {
        String nullEddr = null;
        assertFalse(validator.isValid(nullEddr, context));
    }

}