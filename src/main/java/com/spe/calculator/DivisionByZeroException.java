package com.spe.calculator;

public class DivisionByZeroException extends RuntimeException {
    public DivisionByZeroException() {
        super("division by zero is not allowed");
    }
}
