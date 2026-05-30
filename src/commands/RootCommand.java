package src.commands;

import src.commands.Member.MemberCommand;
import src.commands.Project.ProjectCommand;
import src.commands.Task.TaskCommand;
import src.controller.Controller;

public class RootCommand extends BaseCommand {
    public RootCommand(Controller controller) {
        super(controller, "root", "r", 0);
        registerSubCommand(new ProjectCommand(controller));
        registerSubCommand(new TaskCommand(controller));
        registerSubCommand(new MemberCommand(controller));
    }
}
