package utils;

import components.core.Book;
import components.core.Order;
import components.core.Request;

import utils.CsvHelper;

public final class CsvMapper {

    // ========== BOOK ==========
    public static String toCsv(Book b, char delimiter) {
        return String.join(String.valueOf(delimiter),
                String.valueOf(b.getId()),
                escape(b.getTitle()),
                escape(b.getAuthor()),
                escape(b.getDescription().isEmpty() ? "No description" : b.getDescription()),
                String.valueOf(b.getTimeStamp()),
                b.getPrice().toString(),
                b.getStatus().name()
        );
    }

    // ========== REQUEST ==========
    public static String toCsv(Request r, char delimiter) {
        return String.join(String.valueOf(delimiter),
                String.valueOf(r.getId()),
                escape(r.getBookTitle()),
                r.getStatus().name()
        );
    }

    // ========== ORDER ==========
    public static String toCsv(Order o, char delimiter) {
        return String.join(String.valueOf(delimiter),
                String.valueOf(o.getId()),
                String.valueOf(o.getBookId()),
                escape(o.getStatus().name()),
                String.valueOf(o.getStartTime()),
                String.valueOf(o.getCompletionTime()),
                o.getPhoneNumber(),
                String.valueOf(o.getDeliveryPrice())
        );
    }

    private static String escape(String s) {
        return CsvHelper.escapeCsv(s);
    }
}