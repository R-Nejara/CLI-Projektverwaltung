package src.controller;

import src.view.View;

public class DefaultController extends BaseController {

    public DefaultController(View view) {
        super(view);
    }

    @Override
    public void run(String[] args) {
        while (true) { 
            String input = view.readUserInput("Enter a command (or 'exit' to quit): ", null, null, false);

            if (input.equalsIgnoreCase("exit")) {
                break;
            } 
            view.printMessage("Input: " + input);
        }
    }
}
