package console.screens;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainMenuScreen implements IScreen<Integer> {
    final private String HEADER = "> This is your book store.";
    final private String PROMPT = "Please, select one of the options:";

    final String[] options = {"Add book", "Remove book\n", "Make request", "Cancel request\n",
            "Make order", "Close order\n", "Show book catalog", "Show request catalog",
            "Show order catalog\n", "Show book archive", "Show request archive",
            "Show order archive\n", "Quit"};

    final private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void show() {
        System.out.println(HEADER);
        System.out.println();
        System.out.println(PROMPT);

        for (int i = 0; i < options.length; ++i) {
            System.out.printf("%d) %s\n", i + 1, options[i]);
        }
    }

    @Override
    public Integer askInput() {
        while (true) {
            System.out.print("\n[INPUT] Your choice: ");

            try {
                String line = br.readLine();
                int choice = Integer.parseInt(line);

                if (1 <= choice && choice <= options.length) {
                    return choice;
                }

                throw new Exception("Not in range");

            } catch (Exception e) {
                System.err.println("[ERROR] Wrong input. Try again.");
            }
        }
    }

    public int getLastIndex() {
        return options.length;
    }
}