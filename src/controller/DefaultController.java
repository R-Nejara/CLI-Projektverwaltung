package src.controller;

import src.menustates.MainMenuState;
import src.menustates.MenuState;
import src.view.View;

public class DefaultController extends BaseController {
    private MenuState currentState;

    public DefaultController(View view) {
        super(view);
        this.currentState = new MainMenuState(this, view);
    }

    @Override
    public void run(String[] args) {
        while (currentState != null) { 
            currentState = currentState.handle();
        }
    }
}
