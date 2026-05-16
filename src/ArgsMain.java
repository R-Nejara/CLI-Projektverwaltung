package src;

import src.controller.ArgsController;
import src.controller.Controller;
import src.controller.DefaultController;

public class ArgsMain {
    public static void main(String[] args) {
        Controller controller = (args.length > 0) ? new ArgsController() : new DefaultController();
        controller.run(args);
    }
}
