package org.example;

import archive.BookArchive;
import archive.OrderArchive;
import archive.RequestArchive;
import core.Book;
import core.Order;
import core.Request;
import manager.BookManager;
import manager.OrderManager;
import manager.RequestManager;
import store.BookStore;

import java.util.Optional;

public class TaskThreeFour {
    public static void main(String[] args) throws Exception {
        BookArchive bookArchive = new BookArchive();
        OrderArchive orderArchive = new OrderArchive();
        RequestArchive requestArchive = new RequestArchive();

        BookStore store = new BookStore("Лабиринт",
                                         new BookManager(bookArchive),
                                         new OrderManager(orderArchive),
                                         new RequestManager(requestArchive));

        /*
        ---------------------------------------------------------------------
        --                 Создаем запросы пользователей                   --
        ---------------------------------------------------------------------
         */
        Request request1 = store.createRequest("Война и Мир");
        Request request2 = store.createRequest("Идиот");
        Request request3 = store.createRequest("Преступление и наказание");
        /*
        ---------------------------------------------------------------------
        --       Добавляем книги в архив (запросы должны пропасть)         --
        ---------------------------------------------------------------------
*/
        Book newBook1 = store.addBook("Война и Мир");
        Book newBook2 = store.addBook("Идиот");
        Book newBook3 = store.addBook("Преступление и наказание");
        /*
        ---------------------------------------------------------------------
        --    Закажем первые 2 книги (их статусы должны измениться)        --
        ---------------------------------------------------------------------
         */
        Order newOrder1 = store.createOrder("Война и Мир");
        Order newOrder2 = store.createOrder("Идиот");
       /*
        ---------------------------------------------------------------------
        --    Закажем заказанную книгу (должна быть ошибка)                --
        ---------------------------------------------------------------------
         */
        // Order newOrder3 = store.createOrder("Война и Мир");
        /*
        ---------------------------------------------------------------------
        --                       Отменим заказы                            --
        ---------------------------------------------------------------------
         */
        Optional<Order> disOrder1 = store.closeOrder(newOrder1.getId());
        Optional<Order> disOrder2 = store.closeOrder(newOrder2.getId());
        /*
        ---------------------------------------------------------------------
        --                        Удаляем по id                            --
        ---------------------------------------------------------------------
         */
        Optional<Book> newBook = store.removeBook(2);
    }
}
