package buffer;

import java.util.ArrayDeque;

public class Buffer {
    private final int size;
    private final ArrayDeque<Integer> queue;

    public Buffer(int size) {
        this.size = size;
        this.queue = new ArrayDeque<>();
    }

    public boolean isFull() {
        return queue.size() >= size;
    }

    public synchronized void put(int num) throws InterruptedException {
        while (isFull()) wait();

        queue.add(num);
        notifyAll();
    }

    public synchronized int pop() throws InterruptedException {
        while (queue.isEmpty()) wait();

        int item = queue.pop();
        notifyAll();

        return item;
    }
}