package com.spe.calculator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CalculatorController.class)
class CalculatorApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addEndpointReturnsResult() throws Exception {
        mockMvc.perform(get("/api/v1/add?a=2&b=3"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value(5.0));
    }

    @Test
    void divideByZeroReturns400() throws Exception {
        mockMvc.perform(get("/api/v1/divide?a=6&b=0"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void missingParamReturns400() throws Exception {
        mockMvc.perform(get("/api/v1/add?a=2"))
            .andExpect(status().isBadRequest());
    }
}
