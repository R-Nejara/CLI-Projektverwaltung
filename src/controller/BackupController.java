package src.controller;

import src.menustates.MainMenuState;
import src.menustates.MenuState;
import src.view.View;

public class BackupController extends BaseController {
    private MenuState currentState;

    public BackupController(View view) {
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
