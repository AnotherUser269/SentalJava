package classes.flowers;

import classes.enums.colors;
import classes.enums.types;

public abstract class Flower {
    public abstract int getPrice();
    public abstract types getType();
    public abstract colors getColor();

    public String toString() {
        return String.format("%s: %s flower. Price: %d rub.", getClass().getSimpleName(), getColor(), getPrice());
    }
}
