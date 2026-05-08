package src.commands;

import src.commands.Project.ProjectCommand;

public class RootCommand extends BaseCommand {
    public RootCommand() {
        super("root", "-r", 0);
        super.registerSubCommand(new ProjectCommand());
        super.registerSubCommand(new ExampleCommand());
    }
}
