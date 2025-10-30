package core;

import enums.OrderStatus;

public class Order {
    private OrderStatus status = OrderStatus.NEW;
    private final int id;
    private final int bookId;

    public Order(int id, int bookId) {
        this.id = id;
        this.bookId = bookId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus newStatus) {
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
}
