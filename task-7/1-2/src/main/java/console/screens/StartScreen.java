package console.screens;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StartScreen implements IScreen<String> {
    final private String HEADER = "> Welcome to the book store!";

    final private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void show() {
        System.out.println(HEADER);
        System.out.println();
    }

    @Override
    public String askInput() {
        while (true) {
            System.out.print("[INPUT] Please, provide a name: ");

            try {
                String line = br.readLine();

                if (line.isEmpty()) {
                    throw new Exception("Empty name is not allowed");
                }

                return line;

            } catch (Exception e) {
                System.err.println("[ERROR] Wrong input. Try again.");
            }
        }
    }
}
