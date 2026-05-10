package src.commands;

import src.commands.Project.ProjectCommand;
import src.controller.Controller;

public class RootCommand extends BaseCommand {
    public RootCommand(Controller controller) {
        super(controller, "root", "-r", 0);
        super.registerSubCommand(new ProjectCommand(controller));
        super.registerSubCommand(new ExampleCommand(controller));
    }
}
