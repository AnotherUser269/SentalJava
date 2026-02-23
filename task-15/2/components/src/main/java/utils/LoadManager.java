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
import components.status_enums.BookStatus;
import components.status_enums.OrderStatus;
import components.status_enums.RequestStatus;
import configs.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class LoadManager {
    private final char delimiter;

    private final String bookArchivePath;
    private final String requestArchivePath;
    private final String orderArchivePath;

    private final String bookCatalogPath;
    private final String requestCatalogPath;
    private final String orderCatalogPath;

    public LoadManager(char delimiter,
                       String bookArchivePath, String requestArchivePath, String orderArchivePath,
                       String bookCatalogPath, String requestCatalogPath, String orderCatalogPath) {
        this.bookArchivePath = bookArchivePath;
        this.requestArchivePath = requestArchivePath;
        this.orderArchivePath = orderArchivePath;

        this.bookCatalogPath = bookCatalogPath;
        this.requestCatalogPath = requestCatalogPath;
        this.orderCatalogPath = orderCatalogPath;

        this.delimiter = delimiter;
    }

    public void loadBookArchive(BookArchive bookArchive) {
        Path path = Paths.get(bookArchivePath);
        bookArchive.clearAll();
        if (!Files.exists(path)) return;

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote(String.valueOf(delimiter)));

                String idStr = parts.length > 0 ? parts[0] : "";
                String bookTitle = parts.length > 1 ? parts[1] : "";
                String authorName = parts.length > 1 ? parts[2] : "";
                String description = parts.length > 1 ? parts[3] : "No description";
                String timeStampStr = parts.length > 1 ? parts[4] : "";
                String priceStr = parts.length > 1 ? parts[5] : "";
                String statusStr = parts.length > 2 ? parts[6] : "";

                int id = idStr.isEmpty() ? 0 : Integer.parseInt(idStr);
                long timeStamp = timeStampStr.isEmpty() ? 0 : Long.parseLong(idStr);
                BigDecimal price = idStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(priceStr);

                Book book = new Book(id, bookTitle, authorName, description, timeStamp, price);

                BookStatus status;

                try {
                    status = BookStatus.valueOf(statusStr.trim());
                } catch (Exception ex) {
                    status = BookStatus.NotInOrder;
                }

                book.setStatus(status);

                if(bookArchive.find(id).isPresent()) {
                    // Replacing then
                    bookArchive.remove(id);
                }

                bookArchive.put(book);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadRequestArchive(RequestArchive requestArchive) {
        Path path = Paths.get(requestArchivePath);
        requestArchive.clearAll();
        if (!Files.exists(path)) return;

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote(String.valueOf(delimiter)));

                String idStr = parts.length > 0 ? parts[0] : "";
                String bookTitle = parts.length > 1 ? parts[1] : "";
                String statusStr = parts.length > 2 ? parts[2] : "";

                int id = idStr.isEmpty() ? 0 : Integer.parseInt(idStr);

                Request request = new Request(id, bookTitle);

                RequestStatus status;

                try {
                    status = RequestStatus.valueOf(statusStr.trim());
                } catch (Exception ex) {
                    status = RequestStatus.Opened;
                }

                request.setStatus(status);

                if(requestArchive.find(id).isPresent()) {
                    // Replacing then
                    requestArchive.remove(id);
                }

                requestArchive.put(request);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadOrderArchive(OrderArchive orderArchive) {
        Path path = Paths.get(orderArchivePath);
        orderArchive.clearAll();
        if (!Files.exists(path)) return;

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote(String.valueOf(delimiter)));

                String idStr = parts.length > 0 ? parts[0] : "";
                String bookIdStr = parts.length > 1 ? parts[1] : "";
                String statusStr = parts.length > 2 ? parts[2] : "";
                String startTimeStr = parts.length > 0 ? parts[3] : "";
                String completionTimeStr = parts.length > 0 ? parts[4] : "";
                String phoneNumber = parts.length > 0 ? parts[5] : "";
                String deliveryPriceStr = parts.length > 0 ? parts[6] : "";

                int id = idStr.isEmpty() ? 0 : Integer.parseInt(idStr);
                int bookId = bookIdStr.isEmpty() ? 0 : Integer.parseInt(bookIdStr);
                long startTime = startTimeStr.isEmpty() ? 0 : Long.parseLong(startTimeStr);
                long completionTime = completionTimeStr.isEmpty() ? 0 : Long.parseLong(completionTimeStr);
                BigDecimal deliveryPrice = deliveryPriceStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(deliveryPriceStr);

                Order order = new Order(id, bookId, startTime, phoneNumber, deliveryPrice);
                order.setCompletionTime(completionTime);

                OrderStatus status;

                try {
                    status = OrderStatus.valueOf(statusStr.trim());
                } catch (Exception ex) {
                    status = OrderStatus.New;
                }

                order.setStatus(status);

                if(orderArchive.find(id).isPresent()) {
                    // Replacing then
                    orderArchive.remove(id);
                }

                orderArchive.put(order);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadBookCatalog(BookCatalog bookCatalog) {
        Path path = Paths.get(bookCatalogPath);
        bookCatalog.clearAll();
        if (!Files.exists(path)) return;

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote(String.valueOf(delimiter)));

                String idStr = parts.length > 0 ? parts[0] : "";
                String bookTitle = parts.length > 1 ? parts[1] : "";
                String authorName = parts.length > 1 ? parts[2] : "";
                String description = parts.length > 1 ? parts[3] : "No description";
                String timeStampStr = parts.length > 1 ? parts[4] : "";
                String priceStr = parts.length > 1 ? parts[5] : "";
                String statusStr = parts.length > 2 ? parts[6] : "";

                int id = idStr.isEmpty() ? 0 : Integer.parseInt(idStr);
                long timeStamp = timeStampStr.isEmpty() ? 0 : Long.parseLong(idStr);
                BigDecimal price = idStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(priceStr);

                Book book = new Book(id, bookTitle, authorName, description, timeStamp, price);

                BookStatus status;

                try {
                    status = BookStatus.valueOf(statusStr.trim());
                } catch (Exception ex) {
                    status = BookStatus.NotInOrder;
                }

                book.setStatus(status);

                if(bookCatalog.get(id).isPresent()) {
                    // Replacing then
                    bookCatalog.remove(id);
                }

                bookCatalog.put(book);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadRequestCatalog(RequestCatalog requestCatalog) {
        Path path = Paths.get(requestCatalogPath);
        requestCatalog.clearAll();
        if (!Files.exists(path)) return;

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote(String.valueOf(delimiter)));

                String idStr = parts.length > 0 ? parts[0] : "";
                String bookTitle = parts.length > 1 ? parts[1] : "";
                String statusStr = parts.length > 2 ? parts[2] : "";

                int id = idStr.isEmpty() ? 0 : Integer.parseInt(idStr);

                Request request = new Request(id, bookTitle);

                RequestStatus status;

                try {
                    status = RequestStatus.valueOf(statusStr.trim());
                } catch (Exception ex) {
                    status = RequestStatus.Opened;
                }

                request.setStatus(status);

                if(requestCatalog.get(id).isPresent()) {
                    // Replacing then
                    requestCatalog.remove(id);
                }

                requestCatalog.put(request);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadOrderCatalog(OrderCatalog orderCatalog) {
        Path path = Paths.get(orderCatalogPath);
        orderCatalog.clearAll();
        if (!Files.exists(path)) return;

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote(String.valueOf(delimiter)));

                String idStr = parts.length > 0 ? parts[0] : "";
                String bookIdStr = parts.length > 1 ? parts[1] : "";
                String statusStr = parts.length > 2 ? parts[2] : "";
                String startTimeStr = parts.length > 0 ? parts[3] : "";
                String completionTimeStr = parts.length > 0 ? parts[4] : "";
                String phoneNumber = parts.length > 0 ? parts[5] : "";
                String deliveryPriceStr = parts.length > 0 ? parts[6] : "";

                int id = idStr.isEmpty() ? 0 : Integer.parseInt(idStr);
                int bookId = bookIdStr.isEmpty() ? 0 : Integer.parseInt(bookIdStr);
                long startTime = startTimeStr.isEmpty() ? 0 : Long.parseLong(startTimeStr);
                long completionTime = completionTimeStr.isEmpty() ? 0 : Long.parseLong(completionTimeStr);
                BigDecimal deliveryPrice = deliveryPriceStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(deliveryPriceStr);

                Order order = new Order(id, bookId, startTime, phoneNumber, deliveryPrice);
                order.setCompletionTime(completionTime);

                OrderStatus status;

                try {
                    status = OrderStatus.valueOf(statusStr.trim());
                } catch (Exception ex) {
                    status = OrderStatus.New;
                }

                order.setStatus(status);

                if(orderCatalog.get(id).isPresent()) {
                    // Replacing then
                    orderCatalog.remove(id);
                }

                orderCatalog.put(order);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
