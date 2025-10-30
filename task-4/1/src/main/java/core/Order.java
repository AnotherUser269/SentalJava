package core;

import enums.OrderStatus;

public class Order {
    private OrderStatus status = OrderStatus.New;
    private final int id;
    private final long startTime;
    private long completionTime;
    private final int bookId;

    public Order(int id, int bookId, long startTime) {
        this.id = id;
        this.bookId = bookId;
        this.startTime = startTime;
        this.completionTime = -1;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus newStatus) {
        if(!newStatus.equals(OrderStatus.New)) {
            this.completionTime = System.currentTimeMillis() / 1000L;
        }

        this.status = newStatus;
    }

    public int getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", status=" + status +
                '}';
    }

    public long getStartTime() {
        return startTime;
    }

    public long getCompletionTime() {
        return completionTime;
    }
}
