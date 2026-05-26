package src;

import src.controller.DefaultController;
import src.view.BackupView;
import src.view.View;

public class Main {
    public static void main(String[] args) {
        View view = new BackupView();
        DefaultController controller = new DefaultController(view);
        controller.run(args);
    }
}
