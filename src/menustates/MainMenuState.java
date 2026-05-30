package src.menustates;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import src.controller.Controller;
import src.view.View;

public class MainMenuState implements MenuState {
    private final View view;
    private final Map<String, Supplier<MenuState>> menuActions = new LinkedHashMap<>();

    public MainMenuState(Controller controller, View view) {
        this.view = view;

        menuActions.put("Projects", () -> new ProjectMenuState(controller, view, this));
        menuActions.put("Tasks", () -> new TaskMenuState(controller, view, this));
        menuActions.put("Members", () -> new MemberMenuState(controller, view, this));
        menuActions.put("Exit", () -> null); 
    }
    
    @Override
    public MenuState handle() {
        final String[] options = menuActions.keySet().toArray(String[]::new);
        final String errorMsg = "Invalid option. Please select a valid option from the menu.";
        final int selection = view.readUserInput(options, errorMsg, true);

        if (selection < 1 || selection > options.length) { return this; }

        final String selectedKey = options[selection - 1];

        return menuActions.get(selectedKey).get();
    }
}