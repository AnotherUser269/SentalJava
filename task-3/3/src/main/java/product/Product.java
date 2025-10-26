package product;

import interfaces.*;

public class Product implements IProduct {
    private IProductPart monitor;
    private IProductPart motherboard;
    private IProductPart shell;

    public IProductPart getMonitor() { return monitor; }
    public IProductPart getMotherboard() { return motherboard; }
    public IProductPart getShell() { return shell; }

    @Override
    public void installFirstPart(IProductPart firstPart) {
        monitor = firstPart;
    }

    @Override
    public void installSecondPart(IProductPart secondPart) {
        motherboard = secondPart;
    }

    @Override
    public void installThirdPart(IProductPart thirdPart) {
        shell = thirdPart;
    }
}
