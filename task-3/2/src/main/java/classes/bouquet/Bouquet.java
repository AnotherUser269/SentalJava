package classes.bouquet;
import classes.flowers.Flower;

public class Bouquet {
    private int currentPrice = 0;
    private int currentAmount = 0;

    public void addFlower(Flower flower) {
        currentAmount++;
        currentPrice += flower.getPrice();
    }

    public int getPrice() {
        return currentPrice;
    }

    public int getAmount() {
        return currentAmount;
    }

    public String toString() {
        return String.format("Bouquet contains of %d flowers. Price: %d rub.", getAmount(), getPrice());
    }
}
