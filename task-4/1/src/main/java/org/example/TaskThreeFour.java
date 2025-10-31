package org.example;

import archive.*;
import catalog.*;
import core.Book;
import core.Order;
import core.Request;
import manager.*;

import sort_enums.BookSort;
import sort_enums.RequestSort;
import status_enums.OrderStatus;
import store.BookStore;

import java.math.BigDecimal;
import java.util.Optional;

public class TaskThreeFour {
    public static void main(String[] args) throws Exception {
        BookCatalog bookCatalog = new BookCatalog();
        OrderCatalog orderCatalog = new OrderCatalog();
        RequestCatalog requestCatalog = new RequestCatalog();

        BookArchive bookArchive = new BookArchive();
        OrderArchive orderArchive = new OrderArchive();
        RequestArchive requestArchive = new RequestArchive();

        BookStore store = new BookStore("Лабиринт",
                new BookManager(bookCatalog, bookArchive),
                new OrderManager(orderCatalog, orderArchive),
                new RequestManager(requestCatalog, requestArchive));


    }
}
