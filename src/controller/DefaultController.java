package src.controller;

import java.util.Scanner;

public class DefaultController extends BaseController {

    @Override
    public void run(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) { 
                System.out.print("> "); // TODO: -> move to View
                String input = scanner.nextLine();


                if (input.isBlank() || input.equals("exit")) { 
                    break; 
                }

                handleInput(input);
            }
            scanner.close();
        }  catch(Exception e) {
            System.err.printf("Error: {%s}\n", e.getLocalizedMessage()); // TODO: -> move to View
        }
    }

    private void handleInput(String input) {
        System.out.printf("Input: %s\n", input);
    }
}
