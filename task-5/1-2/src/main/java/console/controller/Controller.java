package console.controller;

import components.archive.*;
import components.catalog.*;
import components.core.*;
import components.manager.*;
import console.screens.*;
import components.status_enums.OrderStatus;
import components.store.BookStore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class Controller {
    private static final int SEPARATOR_LENGTH = 50;

    private static Controller INSTANCE;

    private Controller() {}

    private final Map<String, IScreen> screens = Map.of(
            ScreenKey.START.key(), new StartScreen(),
            ScreenKey.MAIN_MENU.key(), new MainMenuScreen(),
            ScreenKey.BOOK_ADD.key(), new BookAddingScreen(),
            ScreenKey.BOOK_REMOVE.key(), new BookRemovingScreen(),
            ScreenKey.ORDER_MAKE.key(), new OrderMakingScreen(),
            ScreenKey.ORDER_CANCEL.key(), new OrderCancellingScreen(),
            ScreenKey.REQUEST_MAKE.key(), new RequestMakingScreen(),
            ScreenKey.REQUEST_CANCEL.key(), new RequestCancellingScreen());

    private final BookCatalog bookCatalog = new BookCatalog();
    private final BookArchive bookArchive = new BookArchive();

    private final OrderCatalog orderCatalog = new OrderCatalog();
    private final OrderArchive orderArchive = new OrderArchive();

    private final RequestCatalog requestCatalog = new RequestCatalog();
    private final RequestArchive requestArchive = new RequestArchive();

    public void run() {
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

            if (MainOptions.fromIndex(userInput) == MainOptions.ADD_BOOK) {
                screens.get("BookAdd").show();
                ArrayList<Object> data = ((BookAddingScreen) screens.get("BookAdd")).askInput();
                Book addedBook = bookStore.addBook(data.get(0).toString(),
                        data.get(1).toString(),
                        data.get(2).toString(),
                        (long) data.get(3),
                        new BigDecimal(data.get(4).toString()));

                System.out.printf("[INFO]: Created: %s\n", addedBook);
            } else if (MainOptions.fromIndex(userInput) == MainOptions.REMOVE_BOOK) {
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
            } else if (MainOptions.fromIndex(userInput) == MainOptions.MAKE_REQUEST) {
                screens.get("RequestMake").show();
                String title = ((RequestMakingScreen) screens.get("RequestMake")).askInput();
                Request addedRequest = bookStore.createRequest(title);

                System.out.printf("[INFO]: Created: %s\n", addedRequest);
            } else if (MainOptions.fromIndex(userInput) == MainOptions.CANCEL_REQUEST) {
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
            } else if (MainOptions.fromIndex(userInput) == MainOptions.MAKE_ORDER) {
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

            } else if (MainOptions.fromIndex(userInput) == MainOptions.CANCEL_ORDER) {
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
            } else if (MainOptions.fromIndex(userInput) == MainOptions.PRINT_BOOK_CATALOG) {
                System.out.println("> Book catalog:");
                bookCatalog.printAll();
            } else if (MainOptions.fromIndex(userInput) == MainOptions.PRINT_REQUEST_CATALOG) {
                System.out.println("> Request catalog:");
                requestCatalog.printAll();
            } else if (MainOptions.fromIndex(userInput) == MainOptions.PRINT_ORDER_CATALOG) {
                System.out.println("> Order catalog:");
                orderCatalog.printAll();
            } else if (MainOptions.fromIndex(userInput) == MainOptions.PRINT_BOOK_ARCHIVE) {
                System.out.println("> Book archive:");
                bookArchive.printAll();
            } else if (MainOptions.fromIndex(userInput) == MainOptions.PRINT_REQUEST_ARCHIVE) {
                System.out.println("> Request archive:");
                requestArchive.printAll();
            } else if (MainOptions.fromIndex(userInput) == MainOptions.PRINT_ORDER_ARCHIVE) {
                System.out.println("> Order archive:");
                orderArchive.printAll();
            }
        }
    }

    public static Controller getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Controller();
        }

        return INSTANCE;
    }
}
