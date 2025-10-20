package classes.flowers;

public class Astra extends Flower {
    final private int PRICE = 1_500;
    final private String TYPE = "Moderate";
    final private String COLOR = "Violet";

    @Override
    public int getPrice() { return PRICE; }

    @Override
    public String getType() { return TYPE; }

    @Override
    public String getColor() { return COLOR; }
}
