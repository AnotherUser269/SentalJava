package console.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;

public class OrderMakingScreen implements IScreen<Object> {
    private final String HEADER = "> You are ordering a book.";
    private final String PROMPT = "[INPUT] Please, answer next questions: ";
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void show() {
        System.out.println(HEADER);
        System.out.println();
        System.out.println(PROMPT);
        System.out.println();
    }

    @Override
    public ArrayList<Object> askInput() {
        String bookTitle;
        long startTime = -1;
        String phoneNumber;
        BigDecimal price;

        ArrayList<Object> data = new ArrayList<>();

        System.out.println("What's the title of a book?: ");
        bookTitle = readInput();

        System.out.print("[OPTIONAL] What's the time of creating the order? (timestamp): ");
        String timeAns = readInput();
        if (!timeAns.isEmpty()) {
            try {
                startTime = Long.parseLong(timeAns);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Wrong number provided. Using default time.");
            }
        }

        do {
            System.out.print("What's your phone number?: ");
            phoneNumber = readInput();
        } while (phoneNumber.isEmpty());

        while (true) {
            System.out.print("What's the price of the delivery?: ");
            try {
                price = new BigDecimal(readInput());

                if (price.compareTo(BigDecimal.ZERO) < 0) {
                    throw new NumberFormatException("Price cannot be below zero");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Wrong price provided. Try again.");
            }
        }

        data.add(bookTitle);
        data.add(startTime);
        data.add(phoneNumber);
        data.add(price);

        return data;
    }

    private String readInput() {
        try {
            return br.readLine();
        } catch (IOException e) {
            System.out.println("[ERROR] Input error: " + e.getMessage());
            return "";
        }
    }
}
