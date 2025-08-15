package com.ejemplo.app1;

public class Main {
    public static void main(String[] args) {
        System.out.println("¡Hola desde Bazel!");
        
        Calculadora calc = new Calculadora();
        int resultado = calc.sumar(5, 3);
        System.out.println("5 + 3 = " + resultado);
    }
}
