package console.screens;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RequestCancellingScreen implements IScreen<Integer> {
    final private String HEADER = "You are trying to cancel a request.";

    final private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void show() {
        System.out.println(HEADER);
        System.out.println();
    }

    @Override
    public Integer askInput() {
        while (true) {
            System.out.print("[INPUT] Please, provide an identificator of the request to remove: ");

            try {
                return Integer.parseInt(br.readLine());
            } catch (Exception e) {
                System.err.println("[ERROR] Wrong input. Try again.");
            }
        }

    }
}
