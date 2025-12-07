class MySwitch {
    private static final Object lock = new Object();
    private static boolean state;

    private static volatile boolean needWait;
    private static volatile boolean needTimeWait;
    private static volatile boolean isDone;

    public static void saveSwitch() {
        synchronized (lock) {
            while (true) {
                state = !state;

                if (needWait) {
                    try {
                        lock.wait();
                    } catch (InterruptedException _) { }
                }

                if (needTimeWait) {
                    try {
                        Thread.sleep(5_000);
                    } catch (InterruptedException _) { }
                }

                if (isDone) break;
            }
        }
    }

    public void lockWake() {
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    public void setNeedWait(boolean state) { needWait = state; }

    public void setNeedTimeWait(boolean state) { needTimeWait = state; }

    public void setIsDone(boolean state) { isDone = state; }

    public void reset() {
        state = false;
        needWait = false;
        needTimeWait = false;
        isDone = false;
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        MySwitch.saveSwitch();
    }

}

public class Task1 {
    public static void main(String[] args) throws InterruptedException {
        MySwitch mySwitch = new MySwitch();

        Thread thread1 = new MyThread();
        Thread thread2 = new MyThread();
        Thread thread3 = new MyThread();

        // New
        printLog(thread1);

        // Runnable
        thread1.start();
        printLog(thread1);

        // Blocked
        thread2.start();
        printLog(thread2);

        // Waiting
        mySwitch.setNeedWait(true);
        printLog(thread1);

        // Terminated
        mySwitch.lockWake();
        mySwitch.setIsDone(true);

        printLog(thread1);

        // Time Waiting
        mySwitch.reset();
        mySwitch.setNeedTimeWait(true);

        thread3.start();
        printLog(thread3);

        mySwitch.setIsDone(true);
    }

    public static void printLog(Thread thread) throws InterruptedException {
        Thread.sleep(1_000);
        System.out.printf("[INFO] %s; %s%n", thread.getName(), thread.getState());
    }
}
