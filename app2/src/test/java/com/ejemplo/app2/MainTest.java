package com.ejemplo.app2;

import org.junit.Test;
import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void testAreaCuadrado() {
        Area area = new Area();
        double lado = 4.0;
        double esperado = 16.0;
        assertEquals(esperado, area.areaCuadrado(lado), 0.0001);
    }

    @Test
    public void testAreaRectangulo() {
        Area area = new Area();
        double base = 5.0;
        double altura = 3.0;
        double esperado = 15.0;
        assertEquals(esperado, area.areaRectangulo(base, altura), 0.0001);
    }

    @Test
    public void testAreaCirculo() {
        Area area = new Area();
        double radio = 2.5;
        double esperado = Math.PI * radio * radio;
        assertEquals(esperado, area.areaCirculo(radio), 0.0001);
    }
}