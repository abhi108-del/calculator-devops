package com.spe.calculator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CalculatorController {

    @GetMapping("/add")
    public CalcResponse add(@RequestParam double a, @RequestParam double b) {
        return new CalcResponse("add", a, b, a + b);
    }

    @GetMapping("/subtract")
    public CalcResponse subtract(@RequestParam double a, @RequestParam double b) {
        return new CalcResponse("subtract", a, b, a - b);
    }

    @GetMapping("/multiply")
    public CalcResponse multiply(@RequestParam double a, @RequestParam double b) {
        return new CalcResponse("multiply", a, b, a * b);
    }

    @GetMapping("/divide")
    public CalcResponse divide(@RequestParam double a, @RequestParam double b) {
        if (b == 0) {
            throw new DivisionByZeroException();
        }
        return new CalcResponse("divide", a, b, a / b);
    }
}
