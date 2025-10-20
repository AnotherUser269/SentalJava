import classes.flowers.*;
import classes.bouquet.*;

public class TaskThreeTwo {
    public static void main(String[] args) {
        Flower[] flowers = {new Astra(), new Rose(), new Orchid(), new Chamomile()};
        Bouquet bouquet = new Bouquet();

        for(Flower i: flowers) {
            bouquet.addFlower(i);
        }

        System.out.println(bouquet);
    }
}
