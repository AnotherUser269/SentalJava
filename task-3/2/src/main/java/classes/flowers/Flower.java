package classes.flowers;

public abstract class Flower {
    public abstract int getPrice();
    public abstract String getType();
    public abstract String getColor();

    public String toString() {
        return String.format("%s: %s flower. Price: %d rub.", getClass().getSimpleName(), getColor(), getPrice());
    }
}
