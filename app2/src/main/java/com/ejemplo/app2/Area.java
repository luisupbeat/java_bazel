package com.ejemplo.app2;

public class Area {
    public double areaCuadrado(double lado) {
        return lado * lado;
    }

    public double areaRectangulo(double base, double altura) {
        return base * altura;
    }

    public double areaCirculo(double radio) {
        return Math.PI * radio * radio;
    }
}