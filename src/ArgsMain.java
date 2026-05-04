package src;

import src.controller.ArgsController;

public class ArgsMain {
    public static void main(String[] args) {
        if (args.length > 0) {
            ArgsController controller = new ArgsController();
            controller.handleInput(args);
        } else {
            main(args);
        }
    }
}
