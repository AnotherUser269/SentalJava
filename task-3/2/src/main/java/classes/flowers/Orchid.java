package classes.flowers;

import classes.enums.*;

public class Orchid extends Flower {
    final private int PRICE = 3_000;
    final private types TYPE = types.RARE;
    final private colors COLOR = colors.WHITE;

    @Override
    public int getPrice() { return PRICE; }

    @Override
    public types getType() { return TYPE; }

    @Override
    public colors getColor() { return COLOR; }
}

