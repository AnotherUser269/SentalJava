package components.core;

import components.status_enums.OrderStatus;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "bookId")
    private Integer bookId;

    @Column(name = "startTime")
    private Long startTime;

    @Column(name = "phoneNumber", length = 25)
    private String phoneNumber;

    @Column(name = "deliveryPrice", precision = 10, scale = 2)
    private BigDecimal deliveryPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 15)
    private OrderStatus status = OrderStatus.New;

    @Column(name = "completionTime")
    private Long completionTime;

    protected Order() {
    }

    public Order(int id, int bookId, long startTime, String phoneNumber,
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
        return String.format("Order{id=%d, bookId=%d, status=%s, phone=%s, price=%s, startTime=%s, completionTime=%s}",
                id, bookId, status, phoneNumber, deliveryPrice, startTime, completionTime);
    }
}
