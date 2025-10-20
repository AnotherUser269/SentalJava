package classes.flowers;

public class Orchid extends Flower {
    final private int PRICE = 3_000;
    final private String TYPE = "Rare";
    final private String COLOR = "White";

    @Override
    public int getPrice() { return PRICE; }

    @Override
    public String getType() { return TYPE; }

    @Override
    public String getColor() { return COLOR; }
}

