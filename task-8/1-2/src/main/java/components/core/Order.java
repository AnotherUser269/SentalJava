package components.core;

import components.status_enums.OrderStatus;
import utils.CsvHelper;

import java.math.BigDecimal;
import java.util.Objects;

public final class Order {
    private final int id;
    private final int bookId;
    private final long startTime;
    private final String phoneNumber;
    private final BigDecimal deliveryPrice;

    private long completionTime;
    private OrderStatus status = OrderStatus.New;

    private Order(int id, int bookId, long startTime, String phoneNumber,
                  BigDecimal deliveryPrice, OrderStatus status, long completionTime) {
        this.id = id;
        this.bookId = bookId;
        this.startTime = startTime;
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber не может быть null").trim();
        this.deliveryPrice = Objects.requireNonNull(deliveryPrice, "deliveryPrice не может быть null");
        this.status = Objects.requireNonNull(status);
        this.completionTime = completionTime;
    }

    public Order(int id, int bookId, long startTime, String phoneNumber, BigDecimal deliveryPrice) {
        this(id, bookId, startTime, phoneNumber, deliveryPrice, OrderStatus.New, -1L);
    }

    public Order(int id, int bookId, long startTime, String phoneNumber) {
        this(id, bookId, startTime, phoneNumber, BigDecimal.ZERO);
    }

    // Getters
    public OrderStatus getStatus() {
        return status;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public int getId() {
        return id;
    }

    public void setStatus(OrderStatus newStatus) {
        if (!newStatus.equals(OrderStatus.New)) {
            this.completionTime = System.currentTimeMillis() / 1000L;
        }

        this.status = newStatus;
    }
    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }
    public int getBookId() {
        return bookId;
    }
    public long getStartTime() {
        return startTime;
    }
    public long getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(long time) {
        this.completionTime = time;
    }

    @Override
    public String toString() {
        return String.format("Order{id=%d, bookId=%d, status=%s, phone=%s, price=%s}",
                id, bookId, status, phoneNumber, deliveryPrice);
    }
}
