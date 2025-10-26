package org.example;

import classes.flowers.*;
import classes.bouquet.*;

public class TaskThreeTwo {
    public static void main(String[] args) {
        String[] orderedFlowers = {"Astra", "Astra", "Rose", "Chamomile", "Orchid", "Rose", "Rose"};
        Bouquet bouquet = new Bouquet();

        for(String i: orderedFlowers) {
            switch (i) {
                case "Astra" -> bouquet.addFlower(new Astra());
                case "Rose" -> bouquet.addFlower(new Rose());
                case "Chamomile" -> bouquet.addFlower(new Chamomile());
                case "Orchid" -> bouquet.addFlower(new Orchid());
                default -> throw new RuntimeException("This flower doesn't exists");
            }
        }

        System.out.println(" - Flowers in the bouquet:");
        bouquet.printContent();

        System.out.println("\n- Bouquet:");
        System.out.println(bouquet);
    }
}
