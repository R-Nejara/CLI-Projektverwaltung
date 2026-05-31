package src.commands.Task;

import src.commands.BaseCommand;
import src.controller.Controller;

public class TaskCommand extends BaseCommand {
    public TaskCommand(Controller controller) {
        super(controller, "task", "t", 0);
        registerSubCommand(new AddTaskCommand(controller));
        registerSubCommand(new ListTaskCommand(controller));
        registerSubCommand(new ShowTaskCommand(controller));
        registerSubCommand(new EditTaskCommand(controller));
        registerSubCommand(new RemoveTaskCommand(controller));
        registerSubCommand(new HelpTaskCommand(controller, this));
    }
}
