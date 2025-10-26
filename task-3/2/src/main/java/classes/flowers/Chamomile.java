package classes.flowers;

import classes.enums.*;

public class Chamomile extends Flower {
    final private int PRICE = 851;
    final private types TYPE = types.MODERATE;
    final private colors COLOR = colors.WHITE;

    @Override
    public int getPrice() { return PRICE; }

    @Override
    public types getType() { return TYPE; }

    @Override
    public colors getColor() { return COLOR; }
}
