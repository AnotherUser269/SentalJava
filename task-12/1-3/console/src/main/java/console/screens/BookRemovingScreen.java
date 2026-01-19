package console.screens;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BookRemovingScreen implements IScreen<Integer> {
    final private String HEADER = "> You are trying to remove a book.";

    final private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void show() {
        System.out.println(HEADER);
        System.out.println();
    }

    @Override
    public Integer askInput() {
        while (true) {
            System.out.print("[INPUT] Please, provide an identificator of the book to remove: ");

            try {
                return Integer.parseInt(br.readLine());
            } catch (Exception e) {
                System.err.println("[ERROR] Wrong input. Try again.");
            }
        }
    }
}
