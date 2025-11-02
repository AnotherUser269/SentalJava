package org.example;

import archive.*;
import catalog.*;
import core.Book;
import core.Order;
import manager.*;

import sort_enums.BookSort;
import sort_enums.OrderSort;
import sort_enums.RequestSort;
import status_enums.OrderStatus;
import store.BookStore;

import java.math.BigDecimal;
import java.util.ArrayList;
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

        /* ----------------------------------------------
                           Подготовка
           ---------------------------------------------- */

        store.createRequest("Это книга 1");
        store.createRequest("А это книга 2");
        store.createRequest("Но это книга 3");

        Book book1 = store.addBook("Это книга 1", "Я", "Это очень короткое описание", 1, BigDecimal.valueOf(70.0));
        Book book2 = store.addBook("А это книга 2", "Я", 100000000000L, BigDecimal.valueOf(8.0));
        Book book3 = store.addBook("Но это книга 3", "Я", 1000000000000000000L, BigDecimal.valueOf(15.6));

        Order testOrder = store.createOrder("Это книга 1", 1, "+1 202-***-0173", BigDecimal.valueOf(0));
        store.createOrder("А это книга 2", 1000, "+49 151 *** 6789", BigDecimal.valueOf(1000));
        store.createOrder("Но это книга 3", 100000000000L, "+7 912-***-6789", BigDecimal.valueOf(12));

        store.createRequest("Это книга 1");
        store.createRequest("Это книга 1");
        store.createRequest("Это книга 1");
        store.createRequest("Это книга 1");
        store.createRequest("Это книга 1");
        store.createRequest("Мой дневник");
        store.createRequest("Идеи для проекта");
        store.createRequest("Чек-лист покупок");
        store.createRequest("Рецепты");
        store.createRequest("Список фильмов для просмотра");
        store.createRequest("store.createRequest");

        /* --------------------------------------------------------------------------------------------
                  Список книг (сортировать по алфавиту, дате издания, цене, наличию на складе)
           -------------------------------------------------------------------------------------------- */
        System.out.println("TEST 1:");

        System.out.println("AlphabetAscending: ");
        bookArchive.sortBy(BookSort.AlphabetAscending);
        bookArchive.printAll();

        System.out.println("DateAscending: ");
        bookArchive.sortBy(BookSort.DateAscending);
        bookArchive.printAll();

        System.out.println("PriceAscending: ");
        bookArchive.sortBy(BookSort.PriceAscending);
        bookArchive.printAll();

        System.out.println("StatusAscending: ");
        bookArchive.sortBy(BookSort.StatusAscending);
        bookArchive.printAll();

        /* --------------------------------------------------------------------------------------------
                     Список заказов (сортировать по дате исполнения, цене, статусу)
           -------------------------------------------------------------------------------------------- */
        System.out.println("\n\nTEST 2:");

        store.closeOrder(0, OrderStatus.Dismissed);
        store.closeOrder(1, OrderStatus.Success);

        System.out.println("EndTimeAscending: ");
        orderArchive.sortBy(OrderSort.EndTimeAscending);
        orderArchive.printAll();

        System.out.println("PriceAscending: ");
        orderArchive.sortBy(OrderSort.PriceAscending);
        orderArchive.printAll();

        System.out.println("StatusAscending: ");
        orderArchive.sortBy(OrderSort.StatusAscending);
        orderArchive.printAll();

        /* --------------------------------------------------------------------------------------------
                     Список запросов на книгу (сортировать по количеству запросов, алфавиту);
           -------------------------------------------------------------------------------------------- */
        System.out.println("\n\nTEST 3:");

        System.out.println("AmountAscending: ");
        requestArchive.sortBy(RequestSort.AmountAscending);
        requestArchive.printAll();

        System.out.println("AlphabetAscending: ");
        requestArchive.sortBy(RequestSort.AlphabetAscending);
        requestArchive.printAll();

        /* --------------------------------------------------------------------------------------------
                     Список выполненных заказов за период времени (сортировать по дате, цене);
           -------------------------------------------------------------------------------------------- */
        System.out.println("\n\nTEST 4:");

        System.out.println("ListOrderedInTime: ");
        System.out.println(orderArchive.getOrderedInTime(1, 10000000000000000L));

        /* --------------------------------------------------------------------------------------------
                            Сумму заработанных средств за период времени;
           -------------------------------------------------------------------------------------------- */
        System.out.println("\n\nTEST 5:");
        BigDecimal totalPrice = BigDecimal.valueOf(0);

        ArrayList<Order> orders = orderArchive.getOrderedInTime(1, 10000000000000000L);

        for(Order order: orders) {
            if(order.getStatus() == OrderStatus.Success) {
                totalPrice = totalPrice.add(order.getDeliveryPrice());

                Optional<Book> orderedBook = bookArchive.find(order.getId());

                if(orderedBook.isPresent()) {
                    totalPrice = totalPrice.add(orderedBook.get().getPrice());
                }
            }
        }

        System.out.println(totalPrice);

        /* --------------------------------------------------------------------------------------------
                            Количество выполненных заказов за период времени;
           -------------------------------------------------------------------------------------------- */
        System.out.println("\n\nTEST 6:");

        System.out.println("AmountOrderedInTime: ");
        System.out.println(orderArchive.amountOrderedInTime(1, 10000000000000000L));

        // Пояснение: всего 3 заказа, но 1 заказ незавершен.

        /* --------------------------------------------------------------------------------------------
          Список «залежавшихся» книг не проданы больше чем 6 мес. (сортировать по дате поступления, цене);
           -------------------------------------------------------------------------------------------- */
        System.out.println("\n\nTEST 7: [см комментарий]");

        // Оно работает, но я не знаю, как проверить, т.к. везде используется текущее время устройство как completionTime

        /* --------------------------------------------------------------------------------------------
                        Посмотреть детали заказа (какие-либо данные заказчика + книги);
           -------------------------------------------------------------------------------------------- */
        System.out.println("\n\nTEST 8:");
        System.out.println(testOrder.getDeliveryPrice());
        System.out.println(testOrder.getPhoneNumber());

        /* --------------------------------------------------------------------------------------------
                                            Посмотреть описание книги.
           -------------------------------------------------------------------------------------------- */
        System.out.println("\n\nTEST 9:");

        System.out.println(book1.getDescription());
    }
}
