package src;

import src.controller.ArgsController;
import src.controller.BackupController;
import src.controller.Controller;
import src.view.BackupView;
import src.view.View;

public class ArgsMain {
    public static void main(String[] args) {
        View view = new BackupView();
        Controller defaultController = new BackupController(view);
        Controller controller = (args.length > 0) ? new ArgsController(view) : defaultController;
        controller.run(args);
    }
}
