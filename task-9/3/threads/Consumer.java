package threads;

import buffer.Buffer;

public class Consumer extends Thread {
    private final int tickMills;
    private final Buffer buffer;

    public Consumer(Buffer buffer, int mills) {
        this.buffer = buffer;
        this.tickMills = mills;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int item = this.buffer.pop();
                System.out.printf("[INFO] Got %d%n", item);

                Thread.sleep(tickMills);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}