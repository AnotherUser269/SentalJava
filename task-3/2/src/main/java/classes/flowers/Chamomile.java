package classes.flowers;

public class Chamomile extends Flower {
    final private int PRICE = 851;
    final private String TYPE = "Moderate";
    final private String COLOR = "Violet";

    @Override
    public int getPrice() { return PRICE; }

    @Override
    public String getType() { return TYPE; }

    @Override
    public String getColor() { return COLOR; }
}
