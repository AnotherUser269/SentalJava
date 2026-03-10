package utils.DTO;

import java.math.BigDecimal;

public class OrderResponse {
    public Integer id;
    public Integer bookId;
    public Long startTime;
    public String phoneNumber;
    public BigDecimal deliveryPrice;
    public String status;
    public Long completionTime;
}