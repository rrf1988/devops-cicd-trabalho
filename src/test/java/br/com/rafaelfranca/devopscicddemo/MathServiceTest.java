package br.com.rafaelfranca.devopscicddemo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MathServiceTest {

    private final MathService mathService = new MathService();

    @Test
    void deveSomarDoisNumeros() {
        assertEquals(5, mathService.somar(2, 3));
    }

    @Test
    void deveSomarComZero() {
        assertEquals(10, mathService.somar(10, 0));
    }
}
