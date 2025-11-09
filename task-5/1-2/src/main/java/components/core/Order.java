package components.core;

import components.status_enums.OrderStatus;

import java.math.BigDecimal;

public class Order {
    private OrderStatus status = OrderStatus.New;
    private final int id;
    private final long startTime;
    private long completionTime;
    private final int bookId;
    private final String phoneNumber;
    private final BigDecimal deliveryPrice;

    public Order(int id, int bookId, long startTime, String phoneNumber, BigDecimal deliveryPrice) {
        this.id = id;
        this.bookId = bookId;
        this.startTime = startTime;
        this.completionTime = -1;
        this.phoneNumber = phoneNumber;
        this.deliveryPrice = deliveryPrice;
    }

    public Order(int id, int bookId, long startTime, String phoneNumber) {
        this(id, bookId, startTime, phoneNumber, BigDecimal.ZERO);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setStatus(OrderStatus newStatus) {
        if (!newStatus.equals(OrderStatus.New)) {
            this.completionTime = System.currentTimeMillis() / 1000L;
        }

        this.status = newStatus;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
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
                ", startTime=" + startTime +
                ", completionTime=" + completionTime +
                ", phoneNumber=" + phoneNumber +
                ", deliveryPrice=" + deliveryPrice +
                '}';
    }

    public long getStartTime() {
        return startTime;
    }

    public long getCompletionTime() {
        return completionTime;
    }
}
