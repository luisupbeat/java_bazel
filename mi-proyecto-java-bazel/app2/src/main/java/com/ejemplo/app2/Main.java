package com.ejemplo.app2;

public class Main {
    public static void main(String[] args) {
        Area area = new Area();

        double lado = 4.0;
        double base = 5.0;
        double altura = 3.0;
        double radio = 2.5;

        System.out.println("Área del cuadrado: " + area.areaCuadrado(lado));
        System.out.println("Área del rectángulo: " + area.areaRectangulo(base, altura));
        System.out.println("Área del círculo: " + area.areaCirculo(radio));
    }
}