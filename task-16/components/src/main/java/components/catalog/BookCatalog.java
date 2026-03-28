package components.catalog;

import components.status_enums.BookStatus;
import components.core.Book;
import components.time.TimeConverter;
import components.time.TimeUnits;
import utils.ConfigLoader;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class BookCatalog implements ICatalog<Book> {
    private final Map<Integer, Book> booksCatalog;
    ConfigLoader configLoader = new ConfigLoader();
    TimeConverter timeConverter = new TimeConverter();

    public BookCatalog() {
        this.booksCatalog = new HashMap<>();
    }

    public List<Book> getNotSold(BigDecimal amount, TimeUnits timeUnit) {
        try {
            BigDecimal defaultTime = new BigDecimal(configLoader.getAppConfig().get("monthsToMarkAsStale").asText());

            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                amount = defaultTime;
                timeUnit = TimeUnits.MONTHS;
            }
        } catch (IOException e) {
            System.err.println("[ERROR] Wrong config, ignoring default value.");
        }

        BigDecimal seconds = BigDecimal.valueOf(timeConverter.toSeconds(amount, timeUnit));
        List<Book> notSoldBooks = new ArrayList<>();

        for (Map.Entry<Integer, Book> i : booksCatalog.entrySet()) {
            long currentEpochSec = System.currentTimeMillis() / 1000L;
            BigDecimal currentBd = BigDecimal.valueOf(currentEpochSec);

            BigDecimal ageBd = BigDecimal.valueOf(i.getValue().getTimeStamp());

            if (i.getValue().getStatus() == BookStatus.InOrder && currentBd.subtract(ageBd).compareTo(seconds) >= 0) {
                notSoldBooks.add(i.getValue());
            }
        }

        return notSoldBooks;
    }

    public Optional<Book> get(String bookTitle) {
        for (Map.Entry<Integer, Book> i : booksCatalog.entrySet()) {
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
        booksCatalog.put(newBook.getId(), newBook);
    }

    @Override
    public Optional<Book> remove(int id) {
        return Optional.ofNullable(booksCatalog.remove(id));
    }

    @Override
    public Optional<Book> get(int id) {
        return Optional.ofNullable(booksCatalog.get(id));
    }

    public ArrayList<Book> getAll() {
        ArrayList<Book> books = new ArrayList<>();

        for (AbstractMap.Entry<Integer, Book> entry : booksCatalog.entrySet()) {
            books.add(entry.getValue());
        }

        return books;
    }

    public void printAll() {
        for (Map.Entry<Integer, Book> entry : booksCatalog.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    public void clearAll() {
        booksCatalog.clear();
    }
}
