package console.screens;

import components.status_enums.OrderStatus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class OrderCancellingScreen implements IScreen<ArrayList<Object>> {
    final private String HEADER = "> You are trying to cancel an order.";

    final private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void show() {
        System.out.println(HEADER);
        System.out.println();
    }

    @Override
    public ArrayList<Object> askInput() {
        int idToRemove;
        OrderStatus reason;
        ArrayList<Object> data = new ArrayList<>();

        while (true) {
            System.out.print("[INPUT] Please, provide an identificator of the order to remove: ");

            try {
                idToRemove = Integer.parseInt(br.readLine());
                break;
            } catch (Exception e) {
                System.out.println("Wrong input. Try again.");
            }
        }

        while (true) {
            System.out.print("Please, provide the reason (1 - Success, 2 - Dismiss): ");

            try {
                int choice = Integer.parseInt(br.readLine());

                if (choice == 1) {
                    reason = OrderStatus.Success;
                } else if (choice == 2) {
                    reason = OrderStatus.Dismissed;
                } else {
                    throw new RuntimeException("Wrong reason");
                }

                break;
            } catch (Exception e) {
                System.out.println("[ERROR] Wrong input. Try again.");
            }
        }

        data.add(idToRemove);
        data.add(reason);

        return data;
    }
}
