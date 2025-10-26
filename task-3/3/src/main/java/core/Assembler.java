package core;

import interfaces.*;
import product.Product;

public class Assembler implements IAssemblyLine{
    private final ILineStep lineStep;

    public Assembler(ILineStep lineStep) {
        this.lineStep = lineStep;
    }

    @Override
    public IProduct assembleProduct() {
        IProductPart monitor = this.lineStep.buildProductPart();
        IProductPart motherBoard = this.lineStep.buildProductPart();
        IProductPart shell = this.lineStep.buildProductPart();

        IProduct computer = new Product();

        computer.installFirstPart(monitor);
        computer.installSecondPart(motherBoard);
        computer.installThirdPart(shell);

        return computer;
    }
}
