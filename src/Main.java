package src;

import src.controller.DefaultController;

public class Main {
    public static void main(String[] args) {
        DefaultController controller = new DefaultController();
        controller.run(args);
    }
}
