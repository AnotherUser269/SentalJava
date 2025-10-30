package org.example;

import archive.*;
import catalog.*;
import core.*;
import manager.*;
import enums.OrderStatus;

import store.BookStore;

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
