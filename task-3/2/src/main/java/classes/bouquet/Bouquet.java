package classes.bouquet;
import classes.flowers.Flower;

import java.util.ArrayList;
import java.util.List;

public class Bouquet {
    private int currentPrice = 0;
    private int currentAmount = 0;
    private final List<Flower> addedFlowers = new ArrayList<>();

    public void addFlower(Flower flower) {
        currentAmount++;
        addedFlowers.add(flower);
        currentPrice += flower.getPrice();
    }

    public int getPrice() {
        return currentPrice;
    }

    public int getAmount() {
        return currentAmount;
    }

    public String toString() {
        return String.format("Bouquet contains %d flowers. Bouquet's price is: %d rub.", getAmount(), getPrice());
    }

    public void printContent() {
        for(Flower flower: addedFlowers) {
            System.out.println(flower);
        }
    }
}
