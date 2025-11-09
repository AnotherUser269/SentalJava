package console.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;

public class BookAddingScreen implements IScreen<Object> {
    private final String HEADER = "> You are adding a book";
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
        String authorName;
        String description;
        long timeStamp = -1;
        BigDecimal price;

        ArrayList<Object> data = new ArrayList<>();

        do {
            System.out.print("What's the title of a book?: ");
            bookTitle = readInput();
        } while (bookTitle.isEmpty());

        do {
            System.out.print("What's the name of the author?: ");
            authorName = readInput();
        } while (authorName.isEmpty());

        System.out.print("[OPTIONAL] What's the description of the book?: ");
        description = readInput();

        System.out.print("[OPTIONAL] What's the time of creating the book? (timestamp): ");
        String timeAns = readInput();
        if (!timeAns.isEmpty()) {
            try {
                timeStamp = Long.parseLong(timeAns);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Wrong number provided. Using default time.");
            }
        }

        while (true) {
            System.out.print("What's the price of the book?: ");
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
        data.add(authorName);
        data.add(description);
        data.add(timeStamp);
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
