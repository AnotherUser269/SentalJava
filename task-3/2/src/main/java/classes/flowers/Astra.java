package classes.flowers;

import classes.enums.*;

public class Astra extends Flower {
    final private int PRICE = 1_500;
    final private types TYPE = types.MODERATE;
    final private colors COLOR = colors.VIOLET;

    @Override
    public int getPrice() { return PRICE; }

    @Override
    public types getType() { return TYPE; }

    @Override
    public colors getColor() { return COLOR; }
}
