package src;

import src.controller.Controller;
import src.controller.DefaultController;
import src.view.DefaultView;
import src.view.View;

public class Main {
    public static void main(String[] args) {
        View view = new DefaultView();
        Controller controller = new DefaultController(view);
        controller.run(args);
    }
}
