class MyThread extends Thread {
    MyThread(String name) { super(name); }

    @Override
    public void run() {
        System.out.printf("Hello, I'm %s!%n", this.getName());
    }
}


public class Task2 {
    public static void main(String[] args) throws InterruptedException {
        Thread firstThread = new MyThread("FirstThread");
        Thread secondThread = new MyThread("SecondThread");

        firstThread.start(); firstThread.join();
        secondThread.start(); secondThread.join();
    }
}