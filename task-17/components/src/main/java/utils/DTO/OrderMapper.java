package utils.DTO;

import components.core.Order;
import components.status_enums.OrderStatus;

public final class OrderMapper {
    private OrderMapper(){}

    public static OrderResponse toResponse(Order o) {
        if (o == null) return null;
        OrderResponse r = new OrderResponse();
        r.id = o.getId();
        r.bookId = o.getBookId();
        r.startTime = o.getStartTime();
        r.phoneNumber = o.getPhoneNumber();
        r.deliveryPrice = o.getDeliveryPrice();
        r.status = o.getStatus() != null ? o.getStatus().name() : null;
        r.completionTime = o.getCompletionTime();
        return r;
    }

    public static Order toEntity(OrderRequest req) {
        if (req == null) return null;
        int id = req.id != null ? req.id : 0;
        long st = req.startTime != null ? req.startTime : System.currentTimeMillis() / 1000L;
        java.math.BigDecimal price = req.deliveryPrice != null ? req.deliveryPrice : java.math.BigDecimal.ZERO;
        OrderStatus s = OrderStatus.New;
        if (req.status != null) {
            try { s = OrderStatus.valueOf(req.status); } catch (IllegalArgumentException ignored) {}
        }
        Order o = new Order(id, req.bookId != null ? req.bookId : 0, st,
                req.phoneNumber != null ? req.phoneNumber : "unknown", price, s,
                req.completionTime != null ? req.completionTime : -1L);
        return o;
    }
}