package src;

import src.controller.ArgsController;
import src.controller.Controller;

public class ArgsMain {
    public static void main(String[] args) {
        if (args.length > 0) {
            Controller controller = new ArgsController();
            controller.run(args);
        } else {
            Main.main(args);
        }
    }
}
