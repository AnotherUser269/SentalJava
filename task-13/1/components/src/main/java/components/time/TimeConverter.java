package components.time;

import java.math.BigDecimal;

public class TimeConverter {
    public long toSeconds(BigDecimal amount, TimeUnits unit) {
        return switch (unit) {
            case SECONDS -> amount.longValue();
            case MINUTES -> amount.multiply(BigDecimal.valueOf(60)).longValue();
            case HOURS   -> amount.multiply(BigDecimal.valueOf(3600)).longValue();
            case DAYS    -> amount.multiply(BigDecimal.valueOf(86400)).longValue();
            case MONTHS  -> amount.multiply(BigDecimal.valueOf(30.4375 * 86400)).longValue();
            case YEARS   -> amount.multiply(BigDecimal.valueOf(365.25 * 86400)).longValue();
        };
    }
}