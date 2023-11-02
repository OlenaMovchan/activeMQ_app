package com.shpp.eddr;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EddrValidator implements ConstraintValidator<EddrConstraint, String> {
    @Override
    public void initialize(EddrConstraint eddrConstraint) {

    }

    @Override
    public boolean isValid(String eddr, ConstraintValidatorContext constraintValidatorContext) {
        if (eddr == null) {
            return false;
        }

        int[] digits = new int[13];
        for (int i = 0; i < eddr.length(); i++) {
            char ch = eddr.charAt(i);
            if (!Character.isDigit(ch)) {
                continue;
            }
            digits[i] = Character.getNumericValue(ch);
        }

        int[] numbers = new int[]{7, 3, 1};
        int checksum = 0;
        int index = 0;
        for (int i = 0; i < 13; i++) {
            if (index == 0) {
                checksum += digits[i] * (numbers[0]);
            } else if (index == 1) {
                checksum += digits[i] * (numbers[1]);
            } else if (index == 2) {
                checksum += digits[i] * (numbers[2]);
            }
            index++;
            if (index == 3) {
                index = 0;
            }
        }
        checksum = checksum % 10;
        return digits[12] == checksum;
    }
}
