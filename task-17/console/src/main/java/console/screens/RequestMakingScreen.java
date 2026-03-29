package console.screens;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RequestMakingScreen implements IScreen<String> {
    final private String HEADER = "> You are requesting a book.";

    final private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void show() {
        System.out.println(HEADER);
        System.out.println();
    }

    @Override
    public String askInput() {
        while (true) {
            System.out.print("[INPUT] Please, provide a title: ");

            try {
                String line = br.readLine();

                if (line.isEmpty()) {
                    throw new Exception("Empty name is not allowed");
                } else if(line.length() > 200) {
                    // VARCHAR(200)
                    throw new Exception("Exceeds VARCHAR(200)");
                }

                return line;

            } catch (Exception e) {
                System.err.println("[ERROR] Wrong input. Try again.");
            }
        }
    }
}
