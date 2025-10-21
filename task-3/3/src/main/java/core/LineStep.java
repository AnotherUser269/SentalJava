package core;

import interfaces.ILineStep;
import interfaces.IProductPart;
import parts.*;

public class LineStep implements ILineStep {
    private int amountCreated = 0;

    @Override
    public IProductPart buildProductPart() {
        if(amountCreated > 2) {
            amountCreated = 0;
        }

        switch (amountCreated) {
            case 0 -> {
                ++amountCreated;
                return new Shell();
            }
            case 1 -> {
                ++amountCreated;
                return new Motherboard();
            }
            case 2 -> {
                ++amountCreated;
                return new Monitor();
            }
            default -> {
                return null;
            }
        }
    }
}
