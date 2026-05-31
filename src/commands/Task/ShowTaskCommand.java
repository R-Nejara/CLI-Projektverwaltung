package src.commands.Task;

import src.commands.BaseCommand;
import src.controller.Controller;

public class ShowTaskCommand extends BaseCommand {
    public ShowTaskCommand(Controller controller) {
        super(controller, "show", "s", 1);
    }

    @Override
    public void execute(String[] args) {
        final String projectName = getArg(args, 0);
        final String taskName = getArg(args, 1);

        controller.showTask(projectName, taskName);
    }

    @Override 
    public String toString() {
        return "\t%s | %s <projectName> <taskName>\n".formatted(getKey(), getShortcut());
    }
}
