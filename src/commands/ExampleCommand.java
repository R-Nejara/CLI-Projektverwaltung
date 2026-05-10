package src.commands;

import src.controller.ArgsController;

public class ExampleCommand extends BaseCommand {
    public ExampleCommand(ArgsController controller) {
        super(controller, "example", "-e", 0);
    }

    @Override
    public void execute(String[] args) {
        System.out.println(super.getKey());
    }
}
