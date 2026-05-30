package src.commands.Task;

import src.commands.BaseCommand;
import src.controller.Controller;

public class ListTaskCommand extends BaseCommand {
    public ListTaskCommand(Controller controller) {
        super(controller, "list", "l", 1);
    }

    @Override
    public void execute(String[] args) {
        final String projectName = getArg(args, 0);
        final String filter = getArg(args, 1);

        controller.listTasks(projectName, filter);
    }

    @Override 
    public String toString() {
        return "\t%s | %s  <projectName> [<filter>]\n".formatted(getKey(), getShortcut());
    }
}
