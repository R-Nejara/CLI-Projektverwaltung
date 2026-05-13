package src.controller;

import java.util.Scanner;

public class DefaultController extends BaseController {

    @Override
    public void run(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) { 
                view.printMessage("> ");
                String input = scanner.nextLine();


                if (input.isBlank() || input.equals("exit")) { 
                    break; 
                }

                handleInput(input);
            }
            scanner.close();
        }  catch(Exception e) {
            view.printError(e.getLocalizedMessage());
        }
    }

    private void handleInput(String input) {
        System.out.printf("Input: %s\n", input);
    }
}
