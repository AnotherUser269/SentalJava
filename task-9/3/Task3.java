import threads.*;
import buffer.*;

public class Task3 {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(5);

        Consumer consumer = new Consumer(buffer, 1000);
        Creator creator = new Creator(buffer, 5);

        consumer.start();
        creator.start();
    }
}
