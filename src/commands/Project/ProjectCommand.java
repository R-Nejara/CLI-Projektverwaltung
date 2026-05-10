package src.commands.Project;

import src.commands.BaseCommand;
import src.controller.ArgsController;

public class ProjectCommand extends BaseCommand {
    public ProjectCommand(ArgsController controller) {
        super(controller, "project", "-p", 0);
        super.registerSubCommand(new AddProjectCommand(controller));
        super.registerSubCommand(new ListProjectCommand(controller));
    }
}
