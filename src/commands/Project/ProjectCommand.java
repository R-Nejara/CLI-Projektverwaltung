package src.commands.Project;

import src.commands.BaseCommand;
import src.controller.Controller;

public class ProjectCommand extends BaseCommand {
    public ProjectCommand(Controller controller) {
        super(controller, "project", "p", 0);
        registerSubCommand(new AddProjectCommand(controller));
        registerSubCommand(new ListProjectCommand(controller));
        registerSubCommand(new ShowProjectCommand(controller));
        registerSubCommand(new EditProjectCommand(controller));
        registerSubCommand(new RemoveProjectCommand(controller));
        registerSubCommand(new HelpProjectCommand(controller, this));
    }
}
