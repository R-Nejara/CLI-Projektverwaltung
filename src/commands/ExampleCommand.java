package src.commands;

import src.controller.Controller;

public class ExampleCommand extends BaseCommand {
    public ExampleCommand(Controller controller) {
        super(controller, "example", "-e", 0);
    }

    @Override
    public void execute(String[] args) {
        System.out.println(super.getKey());
    }
}
