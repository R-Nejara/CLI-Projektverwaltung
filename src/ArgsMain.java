package src;

import src.controller.ArgsController;
import src.controller.Controller;
import src.controller.DefaultController;
import src.view.BackupView;
import src.view.View;

public class ArgsMain {
    public static void main(String[] args) {
        View view = new BackupView();
        Controller controller = (args.length > 0) ? new ArgsController(view) : new DefaultController(view);
        controller.run(args);
    }
}
