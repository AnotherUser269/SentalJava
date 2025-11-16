package utils;

import components.archive.BookArchive;
import components.archive.OrderArchive;
import components.archive.RequestArchive;
import components.catalog.BookCatalog;
import components.catalog.OrderCatalog;
import components.catalog.RequestCatalog;
import components.core.Book;
import components.core.Order;
import components.core.Request;
import configs.AppConfig;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SaveManager {
    private final char delimiter;

    private final String bookArchivePath = AppConfig.bookArchiveFilePath;
    private final String requestArchivePath = AppConfig.requestArchiveFilePath;
    private final String orderArchivePath = AppConfig.orderArchiveFilePath;

    private final String bookCatalogPath = AppConfig.bookCatalogFilePath;
    private final String requestCatalogPath = AppConfig.requestCatalogFilePath;
    private final String orderCatalogPath = AppConfig.orderCatalogFilePath;

    public SaveManager(char delimiter) {
        this.delimiter = delimiter;
    }

    public SaveManager() {
        this(',');
    }

    public void saveBookArchive(BookArchive bookArchive) {
        List<Book> books = bookArchive.getAll();

        Path path = Paths.get(bookArchivePath);

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write("Id,Title,Author,Description,TimeStamp,Price,Status".replace(',', delimiter));
            writer.newLine();
            for (Book book : books) {
                writer.write(book.toCsv(delimiter));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveRequestArchive(RequestArchive requestArchive) {
        List<Request> requests = requestArchive.getAll();

        Path path = Paths.get(requestArchivePath);

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write("Id,BookTitle,Status".replace(',', delimiter));
            writer.newLine();
            for (Request request : requests) {
                writer.write(request.toCsv(delimiter));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveOrderArchive(OrderArchive orderArchive) {
        List<Order> orders = orderArchive.getAll();

        Path path = Paths.get(orderArchivePath);

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write("Id,BookId,Status,StartTime,CompletionTime,PhoneNumber,DeliveryPrice".replace(',', delimiter));
            writer.newLine();
            for (Order order : orders) {
                writer.write(order.toCsv(delimiter));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveBookCatalog(BookCatalog bookCatalog) {
        List<Book> books = bookCatalog.getAll();

        Path path = Paths.get(bookCatalogPath);

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write("Id,Title,Author,Description,TimeStamp,Price,Status".replace(',', delimiter));
            writer.newLine();
            for (Book book : books) {
                writer.write(book.toCsv(delimiter));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveRequestCatalog(RequestCatalog requestCatalog) {
        List<Request> requests = requestCatalog.getAll();

        Path path = Paths.get(requestCatalogPath);

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write("Id,BookTitle,Status".replace(',', delimiter));
            writer.newLine();
            for (Request request : requests) {
                writer.write(request.toCsv(delimiter));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveOrderCatalog(OrderCatalog orderCatalog) {
        List<Order> orders = orderCatalog.getAll();

        Path path = Paths.get(orderCatalogPath);

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write("Id,BookId,Status,StartTime,CompletionTime,PhoneNumber,DeliveryPrice".replace(',', delimiter));
            writer.newLine();
            for (Order order : orders) {
                writer.write(order.toCsv(delimiter));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
