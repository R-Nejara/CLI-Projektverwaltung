package src.commands.Task;

import src.commands.BaseCommand;
import src.controller.Controller;

public class ListTaskCommand extends BaseCommand {
    public ListTaskCommand(Controller controller) {
        super(controller, "list", "l", 1);
    }

    @Override
    public void execute(String[] args) {
        String projectName = (args.length > 0) ? args[0] : null;
        String filter = (args.length > 1) ? args[1] : null;

        controller.listTasks(projectName, filter);
    }

    @Override 
    public String toString() {
        return "\t%s | %s <projectName> [<filter>]\n".formatted(super.getKey(), super.getShortcut());
    }
}
