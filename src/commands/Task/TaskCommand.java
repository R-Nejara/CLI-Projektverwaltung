package src.commands.Task;

import src.commands.BaseCommand;
import src.controller.Controller;

public class TaskCommand extends BaseCommand {
    public TaskCommand(Controller controller) {
        super(controller, "task", "t", 0);
        super.registerSubCommand(new AddTaskCommand(controller));
        super.registerSubCommand(new EditTaskCommand(controller));
        super.registerSubCommand(new RemoveTaskCommand(controller));
        super.registerSubCommand(new HelpTaskCommand(controller, this));
    }
}
