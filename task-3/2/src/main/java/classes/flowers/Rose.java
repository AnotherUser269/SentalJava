package classes.flowers;

public class Rose extends Flower {
    final private int PRICE = 2_680;
    final private String TYPE = "Rare";
    final private String COLOR = "Red";

    @Override
    public int getPrice() { return PRICE; }

    @Override
    public String getType() { return TYPE; }

    @Override
    public String getColor() { return COLOR; }
}
