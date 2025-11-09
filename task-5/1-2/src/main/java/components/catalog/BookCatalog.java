package components.catalog;

import components.status_enums.BookStatus;
import components.core.Book;
import components.time.TimeUnits;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class BookCatalog implements ICatalog<Book> {
    private final Map<Integer, Book> books;

    public BookCatalog() {
        this.books = new HashMap<>();
    }

    public ArrayList<Book> getNotSold(BigDecimal amount, TimeUnits timeUnit) {
        BigDecimal seconds = toSeconds(amount, timeUnit);
        ArrayList<Book> notSoldBooks = new ArrayList<>();

        for (Map.Entry<Integer, Book> i : books.entrySet()) {
            long currentEpochSec = System.currentTimeMillis() / 1000L;
            BigDecimal currentBd = BigDecimal.valueOf(currentEpochSec);

            BigDecimal ageBd = BigDecimal.valueOf(i.getValue().getTimeStamp());

            if (i.getValue().getStatus() == BookStatus.InOrder && currentBd.subtract(ageBd).compareTo(seconds) >= 0) {
                notSoldBooks.add(i.getValue());
            }
        }

        return notSoldBooks;
    }

    private BigDecimal toSeconds(BigDecimal amount, TimeUnits timeUnit) {
        final BigDecimal SECONDS = BigDecimal.ONE;
        final BigDecimal MINUTE = BigDecimal.valueOf(60);
        final BigDecimal HOUR = MINUTE.multiply(MINUTE);
        final BigDecimal DAY = HOUR.multiply(BigDecimal.valueOf(24));

        switch (timeUnit) {
            case Seconds:
                return amount.multiply(SECONDS);
            case Minutes:
                return amount.multiply(MINUTE);
            case Hours:
                return amount.multiply(HOUR);
            case Days:
                return amount.multiply(DAY);
            case Months:
                BigDecimal avgMonthDays = BigDecimal.valueOf(30.5);
                return amount.multiply(avgMonthDays).multiply(DAY);
            case Years:
                BigDecimal avgYearDays = BigDecimal.valueOf(365);
                return amount.multiply(avgYearDays).multiply(DAY);
            default:
                throw new IllegalArgumentException("Unknown time unit: " + timeUnit);
        }
    }

    public Optional<Book> get(String bookTitle) {
        for (Map.Entry<Integer, Book> i : books.entrySet()) {
            Book currentBook = i.getValue();

            if (Objects.equals(currentBook.getTitle(), bookTitle) &&
                    (currentBook.getStatus() == BookStatus.InOrder)) {

                return Optional.ofNullable(i.getValue());
            }
        }
        return Optional.empty();
    }

    @Override
    public void put(Book newBook) {
        books.put(newBook.getId(), newBook);
    }

    @Override
    public Optional<Book> remove(int id) {
        return Optional.ofNullable(books.remove(id));
    }

    @Override
    public Optional<Book> get(int id) {
        return Optional.ofNullable(books.get(id));
    }

    public void printAll() {
        for (Map.Entry<Integer, Book> entry : books.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
