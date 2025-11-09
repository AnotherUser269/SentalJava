package console.controller;

import components.archive.BookArchive;
import components.archive.OrderArchive;
import components.archive.RequestArchive;
import components.catalog.BookCatalog;
import components.catalog.OrderCatalog;
import components.catalog.RequestCatalog;
import components.core.Book;
import components.core.Order;
import components.core.Request;
import components.manager.BookManager;
import components.manager.OrderManager;
import components.manager.RequestManager;
import components.status_enums.OrderStatus;
import components.store.BookStore;
import console.screens.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class Controller {
    // Constants
    private static final int OPTION_ADD_BOOK = 1;
    private static final int OPTION_REMOVE_BOOK = 2;
    private static final int OPTION_MAKE_REQUEST = 3;
    private static final int OPTION_CANCEL_REQUEST = 4;
    private static final int OPTION_MAKE_ORDER = 5;
    private static final int OPTION_CANCEL_ORDER = 6;
    private static final int OPTION_PRINT_BOOK_CATALOG = 7;
    private static final int OPTION_PRINT_REQUEST_CATALOG = 8;
    private static final int OPTION_PRINT_ORDER_CATALOG = 9;
    private static final int OPTION_PRINT_BOOK_ARCHIVE = 10;
    private static final int OPTION_PRINT_REQUEST_ARCHIVE = 11;
    private static final int OPTION_PRINT_ORDER_ARCHIVE = 12;

    // UI constants
    private static final int SEPARATOR_LENGTH = 50;

    private final Map<String, IScreen> screens = Map.of("Start", new StartScreen(),
            "MainMenu", new MainMenuScreen(),
            "BookAdd", new BookAddingScreen(),
            "BookRemove", new BookRemovingScreen(),
            "OrderMake", new OrderMakingScreen(),
            "OrderCancel", new OrderCancellingScreen(),
            "RequestMake", new RequestMakingScreen(),
            "RequestCancel", new RequestCancellingScreen());

    private final BookCatalog bookCatalog = new BookCatalog();
    private final BookArchive bookArchive = new BookArchive();

    private final OrderCatalog orderCatalog = new OrderCatalog();
    private final OrderArchive orderArchive = new OrderArchive();

    private final RequestCatalog requestCatalog = new RequestCatalog();
    private final RequestArchive requestArchive = new RequestArchive();

    public void run() {
        // Preparation
        screens.get("Start").show();
        BookStore bookStore = new BookStore((String) screens.get("Start").askInput(),
                new BookManager(bookCatalog, bookArchive),
                new OrderManager(orderCatalog, orderArchive),
                new RequestManager(requestCatalog, requestArchive));

        // Main menu
        int userInput = 0;
        int lastIndex = ((MainMenuScreen) screens.get("MainMenu")).getLastIndex();

        while (userInput != lastIndex) {
            System.out.println("-".repeat(SEPARATOR_LENGTH));
            screens.get("MainMenu").show();
            userInput = (int) screens.get("MainMenu").askInput();
            System.out.println("-".repeat(SEPARATOR_LENGTH));

            if (userInput == OPTION_ADD_BOOK) {
                screens.get("BookAdd").show();
                ArrayList<Object> data = ((BookAddingScreen) screens.get("BookAdd")).askInput();
                Book addedBook = bookStore.addBook(data.get(0).toString(),
                        data.get(1).toString(),
                        data.get(2).toString(),
                        (long) data.get(3),
                        new BigDecimal(data.get(4).toString()));

                System.out.printf("[INFO]: Created: %s\n", addedBook);
            } else if (userInput == OPTION_REMOVE_BOOK) {
                screens.get("BookRemove").show();
                Integer idToRemove = ((BookRemovingScreen) screens.get("BookRemove")).askInput();

                try {
                    Optional<Book> removedBook = bookStore.removeBook(idToRemove);

                    if (removedBook.isPresent()) {
                        System.out.println("[INFO] Successfully deleted");
                    } else {
                        System.out.println("[ERROR] Id doesn't exist");
                    }
                } catch (Exception e) {
                    System.out.printf("[ERROR] %s\n", e.getMessage());
                }
            } else if (userInput == OPTION_MAKE_REQUEST) {
                screens.get("RequestMake").show();
                String title = ((RequestMakingScreen) screens.get("RequestMake")).askInput();
                Request addedRequest = bookStore.createRequest(title);

                System.out.printf("[INFO]: Created: %s\n", addedRequest);
            } else if (userInput == OPTION_CANCEL_REQUEST) {
                screens.get("RequestCancel").show();
                int idToRemove = ((RequestCancellingScreen) screens.get("RequestCancel")).askInput();

                try {
                    Optional<Request> canceledRequest = bookStore.cancelRequest(idToRemove);

                    if (canceledRequest.isPresent()) {
                        System.out.println("[INFO] Successfully deleted");
                    } else {
                        System.out.println("[ERROR] Id doesn't exist");
                    }
                } catch (Exception e) {
                    System.out.printf("[ERROR] %s\n", e.getMessage());
                }
            } else if (userInput == OPTION_MAKE_ORDER) {
                screens.get("OrderMake").show();

                ArrayList<Object> data = ((OrderMakingScreen) screens.get("OrderMake")).askInput();

                try {
                    Order newOrder = bookStore.createOrder(data.get(0).toString(),
                            (long) data.get(1),
                            data.get(2).toString(),
                            new BigDecimal(data.get(3).toString()));

                    System.out.printf("[INFO]: Created: %s\n", newOrder);
                } catch (Exception e) {
                    System.out.printf("[ERROR] %s\n", e.getMessage());
                }

            } else if (userInput == OPTION_CANCEL_ORDER) {
                screens.get("OrderCancel").show();
                ArrayList<Object> data = ((OrderCancellingScreen) screens.get("OrderCancel")).askInput();

                try {
                    Optional<Order> canceledRequest = bookStore.closeOrder((int) data.get(0), (OrderStatus) data.get(1));

                    if (canceledRequest.isPresent()) {
                        System.out.println("[INFO] Successfully deleted");
                    } else {
                        System.out.println("[ERROR] Id doesn't exist");
                    }
                } catch (Exception e) {
                    System.out.printf("[ERROR] %s\n", e.getMessage());
                }
            } else if (userInput == OPTION_PRINT_BOOK_CATALOG) {
                System.out.println("> Book catalog:");
                bookCatalog.printAll();
            } else if (userInput == OPTION_PRINT_REQUEST_CATALOG) {
                System.out.println("> Request catalog:");
                requestCatalog.printAll();
            } else if (userInput == OPTION_PRINT_ORDER_CATALOG) {
                System.out.println("> Order catalog:");
                orderCatalog.printAll();
            } else if (userInput == OPTION_PRINT_BOOK_ARCHIVE) {
                System.out.println("> Book archive:");
                bookArchive.printAll();
            } else if (userInput == OPTION_PRINT_REQUEST_ARCHIVE) {
                System.out.println("> Request archive:");
                requestArchive.printAll();
            } else if (userInput == OPTION_PRINT_ORDER_ARCHIVE) {
                System.out.println("> Order archive:");
                orderArchive.printAll();
            }
        }
    }
}
