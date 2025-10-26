package org.example;

import core.*;
import interfaces.IProduct;

public class TaskThreeThree {
    public static void main(String[] args) {
        // Конструирование
        LineStep factoryParts = new LineStep();
        Assembler assembler = new Assembler(factoryParts);

        // Запуск
        IProduct computer = assembler.assembleProduct();
        System.out.println(computer);
    }
}