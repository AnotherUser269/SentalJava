package console.controller;

import components.archive.*;
import components.catalog.*;
import components.core.*;
import components.manager.*;
import configs.AppConfig;
import configs.ConfigInjector;
import console.screens.*;
import components.status_enums.OrderStatus;
import components.store.BookStore;
import utils.DAO.BookDAO;
import utils.DAO.OrderDAO;
import utils.DAO.RequestDAO;
import utils.JPALoader;
import utils.JPASaver;
import utils.LoadManager;
import utils.SaveManager;
import utils.sql.ConnectionDBManager;
import utils.sql.SQLLoader;
import utils.sql.SQLSaver;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Controller {
    private static final int SEPARATOR_LENGTH = 50;

    private static Controller INSTANCE;
    private static final Logger logger = LogManager.getLogger(Controller.class);

    private Controller() {
    }

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

    public void run() throws IOException {
        screens.get("Start").show();
        BookStore bookStore = new BookStore((String) screens.get("Start").askInput(),
                new BookManager(bookCatalog, bookArchive),
                new OrderManager(orderCatalog, orderArchive),
                new RequestManager(requestCatalog, requestArchive));


        SaveManager saveManager;
        LoadManager loadManager;


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();

        JPALoader sqlLoader;
        JPASaver sqlSaver;

        BookDAO bookDAO = new BookDAO(em);
        RequestDAO requestDAO = new RequestDAO(em);
        OrderDAO orderDAO = new OrderDAO(em);

        try {
            final String configFile = Paths.get( "configs", "src", "main", "java", "configs", "AppConfig.json").toString();
            ConfigInjector injector = new ConfigInjector(configFile);

            AppConfig cfg = new AppConfig();
            injector.inject(cfg);

            saveManager = new SaveManager(cfg);
            loadManager = new LoadManager(cfg);

            sqlLoader = new JPALoader(em, bookDAO, orderDAO, requestDAO);
            sqlSaver = new JPASaver(em, bookDAO, orderDAO, requestDAO);
        } catch (Exception e) {
            logger.error("Error while loading a config file.");
            throw new RuntimeException("Got error while loading config: " + e.getMessage());
        }


        // Main menu
        int userInput = 0;
        int lastIndex = ((MainMenuScreen) screens.get("MainMenu")).getLastIndex();

        while (userInput != lastIndex) {
            System.out.println("-".repeat(SEPARATOR_LENGTH));
            screens.get("MainMenu").show();

            logger.info("Waiting user input.");

            userInput = (int) screens.get("MainMenu").askInput();
            System.out.println("-".repeat(SEPARATOR_LENGTH));

            logger.info("Got {}.", userInput);

            if (MainOptions.fromIndex(userInput) == MainOptions.ADD_BOOK) {
                logger.info("Adding book.");

                screens.get("BookAdd").show();
                List<Object> data = ((BookAddingScreen) screens.get("BookAdd")).askInput();
                Book addedBook = bookStore.addBook(data.get(0).toString(),
                        data.get(1).toString(),
                        data.get(2).toString(),
                        (long) data.get(3),
                        new BigDecimal(data.get(4).toString()));

                System.out.printf("[INFO]: Created: %s\n", addedBook);

                logger.info("Book was successfully added.");
            } else if (MainOptions.fromIndex(userInput) == MainOptions.REMOVE_BOOK) {
                logger.info("Removing book.");

                screens.get("BookRemove").show();
                Integer idToRemove = ((BookRemovingScreen) screens.get("BookRemove")).askInput();

                try {
                    Optional<Book> removedBook = bookStore.removeBook(idToRemove);

                    if (removedBook.isPresent()) {
                        System.out.println("[INFO] Successfully deleted");
                        logger.info("Book was successfully removed.");
                    } else {
                        System.out.println("[ERROR] Id doesn't exist");
                        logger.error("Book id doesn't exist.");
                    }
                } catch (Exception e) {
                    System.out.printf("[ERROR] %s\n", e.getMessage());
                    logger.error("{}", e.getMessage());
                }
            } else if (MainOptions.fromIndex(userInput) == MainOptions.MAKE_REQUEST) {
                logger.info("Making request.");

                screens.get("RequestMake").show();
                String title = ((RequestMakingScreen) screens.get("RequestMake")).askInput();
                Request addedRequest = bookStore.createRequest(title);

                System.out.printf("[INFO]: Created: %s\n", addedRequest);
                logger.info("Request was successfully created.");
            } else if (MainOptions.fromIndex(userInput) == MainOptions.CANCEL_REQUEST) {
                logger.info("Cancelling request.");
                screens.get("RequestCancel").show();
                int idToRemove = ((RequestCancellingScreen) screens.get("RequestCancel")).askInput();

                try {
                    Optional<Request> canceledRequest = bookStore.cancelRequest(idToRemove);

                    if (canceledRequest.isPresent()) {
                        System.out.println("[INFO] Successfully deleted");
                        logger.info("Request was successfully cancelled.");
                    } else {
                        System.out.println("[ERROR] Id doesn't exist");
                        logger.error("Request id doesn't exist.");
                    }
                } catch (Exception e) {
                    System.out.printf("[ERROR] %s\n", e.getMessage());
                    logger.error("{}", e.getMessage());
                }
            } else if (MainOptions.fromIndex(userInput) == MainOptions.MAKE_ORDER) {
                screens.get("OrderMake").show();

                List<Object> data = ((OrderMakingScreen) screens.get("OrderMake")).askInput();

                try {
                    Order newOrder = bookStore.createOrder(data.get(0).toString(),
                            (long) data.get(1),
                            data.get(2).toString(),
                            new BigDecimal(data.get(3).toString()));

                    System.out.printf("[INFO]: Created: %s\n", newOrder);
                    logger.info("Order was successfully created.");
                } catch (Exception e) {
                    System.out.printf("[ERROR] %s\n", e.getMessage());
                    logger.error("{}", e.getMessage());
                }

            } else if (MainOptions.fromIndex(userInput) == MainOptions.CANCEL_ORDER) {
                screens.get("OrderCancel").show();
                List<Object> data = ((OrderCancellingScreen) screens.get("OrderCancel")).askInput();

                try {
                    Optional<Order> canceledRequest = bookStore.closeOrder((int) data.get(0), (OrderStatus) data.get(1));

                    if (canceledRequest.isPresent()) {
                        System.out.println("[INFO] Successfully deleted");
                        logger.info("Order was successfully removed.");
                    } else {
                        System.out.println("[ERROR] Id doesn't exist");
                        logger.error("Order id doesn't exist.");
                    }
                } catch (Exception e) {
                    System.out.printf("[ERROR] %s\n", e.getMessage());
                    logger.error("{}", e.getMessage());
                }
            } else if (MainOptions.fromIndex(userInput) == MainOptions.PRINT_BOOK_CATALOG) {
                System.out.println("> Book catalog:");
                logger.info("Outputting book catalog.");
                bookCatalog.printAll();
            } else if (MainOptions.fromIndex(userInput) == MainOptions.PRINT_REQUEST_CATALOG) {
                System.out.println("> Request catalog:");
                logger.info("Outputting request catalog.");
                requestCatalog.printAll();
            } else if (MainOptions.fromIndex(userInput) == MainOptions.PRINT_ORDER_CATALOG) {
                System.out.println("> Order catalog:");
                logger.info("Outputting order catalog.");
                orderCatalog.printAll();
            } else if (MainOptions.fromIndex(userInput) == MainOptions.PRINT_BOOK_ARCHIVE) {
                System.out.println("> Book archive:");
                logger.info("Outputting book archive.");
                bookArchive.printAll();
            } else if (MainOptions.fromIndex(userInput) == MainOptions.PRINT_REQUEST_ARCHIVE) {
                System.out.println("> Request archive:");
                logger.info("Outputting request archive.");
                requestArchive.printAll();
            } else if (MainOptions.fromIndex(userInput) == MainOptions.PRINT_ORDER_ARCHIVE) {
                System.out.println("> Order archive:");
                logger.info("Outputting order archive.");
                orderArchive.printAll();
            } else if (MainOptions.fromIndex(userInput) == MainOptions.SAVE_BOOK_ARCHIVE_TO_FILE) {
                try {
                    saveManager.saveBookArchive(bookArchive);
                    System.out.println("[INFO]: Success");
                    logger.info("BookArchive data was successfully saved to a file.");
                } catch (Exception e) {
                    System.err.println("[ERROR]: " + e.getMessage());
                    logger.error("Got error while saving book archive to a file: {}.", e.getMessage());
                }
            } else if (MainOptions.fromIndex(userInput) == MainOptions.SAVE_REQUEST_ARCHIVE_TO_FILE) {
                try {
                    saveManager.saveRequestArchive(requestArchive);
                    System.out.println("[INFO]: Success");
                    logger.info("requestArchive data was successfully saved to a file.");
                } catch (Exception e) {
                    System.err.println("[ERROR]: " + e.getMessage());
                    logger.error("Got error while saving request archive to a file: {}.", e.getMessage());
                }
            } else if (MainOptions.fromIndex(userInput) == MainOptions.SAVE_ORDER_ARCHIVE_TO_FILE) {
                try {
                    saveManager.saveOrderArchive(orderArchive);
                    System.out.println("[INFO]: Success");
                    logger.info("orderArchive data was successfully saved to a file.");
                } catch (Exception e) {
                    System.err.println("[ERROR]: " + e.getMessage());
                    logger.error("Got error while saving order archive to a file: {}.", e.getMessage());
                }
            } else if (MainOptions.fromIndex(userInput) == MainOptions.SAVE_BOOK_CATALOG_TO_FILE) {
                try {
                    saveManager.saveBookCatalog(bookCatalog);
                    System.out.println("[INFO]: Success");
                    logger.info("bookCatalog data was successfully saved to a file.");
                } catch (Exception e) {
                    System.err.println("[ERROR]: " + e.getMessage());
                    logger.error("Got error while saving book catalog to a file: {}.", e.getMessage());
                }
            } else if (MainOptions.fromIndex(userInput) == MainOptions.SAVE_REQUEST_CATALOG_TO_FILE) {
                try {
                    saveManager.saveRequestCatalog(requestCatalog);
                    System.out.println("[INFO]: Success");
                    logger.info("requestCatalog data was successfully saved to a file.");
                } catch (Exception e) {
                    System.err.println("[ERROR]: " + e.getMessage());
                    logger.error("Got error while saving request catalog to a file: {}.", e.getMessage());
                }
            } else if (MainOptions.fromIndex(userInput) == MainOptions.SAVE_ORDER_CATALOG_TO_FILE) {
                try {
                    saveManager.saveOrderCatalog(orderCatalog);
                    System.out.println("[INFO]: Success");
                    logger.info("orderCatalog data was successfully saved to a file.");
                } catch (Exception e) {
                    System.err.println("[ERROR]: " + e.getMessage());
                    logger.error("Got error while saving order catalog to a file: {}.", e.getMessage());
                }
            } else if (MainOptions.fromIndex(userInput) == MainOptions.LOAD_BOOK_ARCHIVE_FROM_FILE) {
                try {
                    loadManager.loadBookArchive(bookArchive);
                    System.out.println("[INFO]: Success");
                    logger.info("bookArchive data was successfully loaded from a file.");
                } catch (Exception e) {
                    System.err.println("[ERROR]: " + e.getMessage());
                    logger.error("Got error while loading book archive from a file: {}.", e.getMessage());
                }
            } else if (MainOptions.fromIndex(userInput) == MainOptions.LOAD_REQUEST_ARCHIVE_FROM_FILE) {
                try {
                    loadManager.loadRequestArchive(requestArchive);
                    System.out.println("[INFO]: Success");
                    logger.info("requestArchive data was successfully loaded from a file.");
                } catch (Exception e) {
                    System.err.println("[ERROR]: " + e.getMessage());
                    logger.error("Got error while loading request archive from a file: {}.", e.getMessage());
                }
            } else if (MainOptions.fromIndex(userInput) == MainOptions.LOAD_ORDER_ARCHIVE_FROM_FILE) {
                try {
                    loadManager.loadOrderArchive(orderArchive);
                    System.out.println("[INFO]: Success");
                    logger.info("orderArchive data was successfully loaded from a file.");
                } catch (Exception e) {
                    System.err.println("[ERROR]: " + e.getMessage());
                    logger.error("Got error while loading order archive from a file: {}.", e.getMessage());
                }
            } else if (MainOptions.fromIndex(userInput) == MainOptions.LOAD_BOOK_CATALOG_FROM_FILE) {
                try {
                    loadManager.loadBookCatalog(bookCatalog);
                    System.out.println("[INFO]: Success");
                    logger.info("bookCatalog data was successfully loaded from a file.");
                } catch (Exception e) {
                    System.err.println("[ERROR]: " + e.getMessage());
                    logger.error("Got error while loading book catalog from a file: {}.", e.getMessage());
                }
            } else if (MainOptions.fromIndex(userInput) == MainOptions.LOAD_REQUEST_CATALOG_FROM_FILE) {
                try {
                    loadManager.loadRequestCatalog(requestCatalog);
                    System.out.println("[INFO]: Success");
                    logger.info("requestCatalog data was successfully loaded from a file.");
                } catch (Exception e) {
                    System.err.println("[ERROR]: " + e.getMessage());
                    logger.error("Got error while loading request catalog from a file: {}.", e.getMessage());
                }
            } else if (MainOptions.fromIndex(userInput) == MainOptions.LOAD_ORDER_CATALOG_FROM_FILE) {
                try {
                    loadManager.loadOrderCatalog(orderCatalog);
                    System.out.println("[INFO]: Success");
                    logger.info("orderCatalog data was successfully loaded from a file.");
                } catch (Exception e) {
                    System.err.println("[ERROR]: " + e.getMessage());
                    logger.error("Got error while loading order catalog from a file: {}.", e.getMessage());
                }
            } else if (MainOptions.fromIndex(userInput) == MainOptions.LOAD_DATABASE) {
                try {
                    sqlLoader.load(bookArchive, bookCatalog,
                            orderArchive, orderCatalog,
                            requestArchive, requestCatalog);
                    System.out.println("[INFO]: Success");
                    logger.info("All data was successfully loaded from a database.");
                } catch (Exception e) {
                    System.err.println("[ERROR]: " + e.getMessage());
                    logger.error("Got error while loading from a db: {}.", e.getMessage());
                }
            } else if (MainOptions.fromIndex(userInput) == MainOptions.SAVE_DATABASE) {
                try {
                    sqlSaver.save(bookArchive, orderArchive, requestArchive);
                    System.out.println("[INFO]: Success");
                    logger.info("All data was successfully saved to a database.");
                } catch (Exception e) {
                    System.err.println("[ERROR]: " + e.getMessage());
                    logger.error("Got error while saving to a db: {}.", e.getMessage());
                }
            } else {
                logger.error("Got unknown command.");
            }
        }

        // ConnectionDBManager.closeConnection(connection);

    }

    public static Controller getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Controller();
        }

        return INSTANCE;
    }
}
