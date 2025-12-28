import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class MyThread extends Thread {
    private final Duration interval;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("hh:mm:ss a");

    MyThread(long time) { this.interval = Duration.ofSeconds(time); }

    @Override
    public void run() {
        try {
            while (true) {
                LocalTime now = LocalTime.now();
                System.out.println(now.format(FMT));

                Thread.sleep(interval);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new RuntimeException(e);
        }
    }

}


public class Task4 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new MyThread(5);

        thread.start();

        // Thread.sleep(60000);
        // thread.interrupt();
    }
}