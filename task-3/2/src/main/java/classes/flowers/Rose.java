package classes.flowers;

import classes.enums.*;

public class Rose extends Flower {
    final private int PRICE = 2_680;
    final private types TYPE = types.NOT_RARE;
    final private colors COLOR = colors.RED;

    @Override
    public int getPrice() { return PRICE; }

    @Override
    public types getType() { return TYPE; }

    @Override
    public colors getColor() { return COLOR; }
}
