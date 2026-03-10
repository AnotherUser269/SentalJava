package console.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    public List<Object> askInput() {
        String bookTitle;
        long startTime = -1;
        String phoneNumber;
        BigDecimal price;

        List<Object> data = new ArrayList<>();

        System.out.println("What's the title of a book?: ");

        // VARCHAR(200)
        do {
            bookTitle = readInput();
        } while(bookTitle.length() > 200);

        System.out.print("[OPTIONAL] What's the time of creating the order? (timestamp): ");
        String timeAns = readInput();
        if (!timeAns.isEmpty()) {
            try {
                startTime = Long.parseLong(timeAns);
            } catch (NumberFormatException e) {
                System.err.println("[ERROR] Wrong number provided. Using default time.");
            }
        }

        // VARCHAR(25)
        do {
            System.out.print("What's your phone number?: ");
            phoneNumber = readInput();
        } while (phoneNumber.isEmpty() || phoneNumber.length() > 25);

        // NUMERIC(10,2)
        while (true) {
            System.out.print("What's the price of the delivery?: ");
            try {
                price = new BigDecimal(readInput());

                int scale = Math.max(price.scale(), 0);

                if (scale > 2) {
                    throw new NumberFormatException("Price must have at most 2 decimal places");
                }

                BigDecimal max = new BigDecimal("99999999.99");
                if (price.abs().compareTo(max) > 0) {
                    throw new NumberFormatException("Price exceeds NUMERIC(10,2) range");
                }

                if (price.compareTo(BigDecimal.ZERO) < 0) {
                    throw new NumberFormatException("Price cannot be below zero");
                }
                break;
            } catch (NumberFormatException e) {
                System.err.println("[ERROR] Wrong price provided. Try again.");
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
            System.err.println("[ERROR] Input error: " + e.getMessage());
            return "";
        }
    }
}
