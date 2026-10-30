package com.spe.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CalculatorControllerTest {

    private final CalculatorController controller = new CalculatorController();

    @Test
    void addsTwoNumbers() {
        assertEquals(5.0, controller.add(2, 3).result());
    }

    @Test
    void subtractsTwoNumbers() {
        assertEquals(-1.0, controller.subtract(2, 3).result());
    }

    @Test
    void multipliesTwoNumbers() {
        assertEquals(6.0, controller.multiply(2, 3).result());
    }

    @Test
    void dividesTwoNumbers() {
        assertEquals(2.0, controller.divide(6, 3).result());
    }

    @Test
    void divideByZeroThrows() {
        assertThrows(DivisionByZeroException.class, () -> controller.divide(6, 0));
    }
}
