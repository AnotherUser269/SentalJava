package org.example;

import java.util.Random;

public class TaskThreeOne {
    public static void main(String[] args) {
        Random random = new Random();
        int finalNum = random.nextInt(900) + 100;
        int digSum = (finalNum/100) + (finalNum/10%10) + finalNum%10;

        System.out.println(finalNum);
        System.out.println(digSum);
    }
}
