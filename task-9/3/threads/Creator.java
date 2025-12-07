package threads;

import buffer.Buffer;

import java.util.Random;

public class Creator extends Thread {
    private final int tickMills;
    private final Buffer buffer;
    private final Random rnd = new Random();

    public Creator(Buffer buffer, int mills) {
        this.buffer = buffer;
        this.tickMills = mills;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int item = rnd.nextInt();
                buffer.put(item);

                System.out.printf("[INFO] Put %d%n", item);

                Thread.sleep(tickMills);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}