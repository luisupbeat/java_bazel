package com.ejemplo.app;

import org.junit.Test;
import static org.junit.Assert.*;

public class MainTest {
    
    @Test
    public void testCalculadoraSumar() {
        Calculadora calc = new Calculadora();
        assertEquals(8, calc.sumar(5, 3));
        assertEquals(0, calc.sumar(-1, 1));
    }
    
    @Test
    public void testCalculadoraRestar() {
        Calculadora calc = new Calculadora();
        assertEquals(2, calc.restar(5, 3));
        assertEquals(-2, calc.restar(3, 5));
    }
}
