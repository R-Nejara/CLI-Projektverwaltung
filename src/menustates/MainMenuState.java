package src.menustates;

import src.controller.Controller;
import src.view.View;

public class MainMenuState implements MenuState {
    private final Controller controller;
    private final View view;

    public MainMenuState(Controller controller, View view) {
        this.controller = controller;
        this.view = view;
    }
    
    @Override
    public MenuState handle() {
        Integer userSelection;
        String[] options = {"Projects", "Tasks", "Members", "Exit"}; 

        userSelection = view.readUserInput(options, "Invalid option. Please select a valid option from the menu.", true);

        switch (userSelection) {
            case 1 -> { return null; }
            case 2 -> { return null; }
            case 3 -> { return null; }
            case 4 -> { return null; }
            default -> {
                view.printError("Invalid option. Please select a valid option from the menu.");
                return this;
            }
        }
    }

}
