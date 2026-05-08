package src.commands.Project;

import src.commands.BaseCommand;

public class ProjectCommand extends BaseCommand {
    public ProjectCommand() {
        super("project", "-p", 0);
        super.registerSubCommand(new AddProjectCommand());
    }
}
