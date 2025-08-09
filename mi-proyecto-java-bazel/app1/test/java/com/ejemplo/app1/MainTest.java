package com.ejemplo.app1;

import static org.junit.Assert.*;
import org.junit.Test;

public class CalculadoraTest {
    @Test
    public void suma_ok() {
        assertEquals(5, Calculadora.sumar(2, 3));
    }
}
