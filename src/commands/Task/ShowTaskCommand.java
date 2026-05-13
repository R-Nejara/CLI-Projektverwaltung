package src.commands.Task;

import src.commands.BaseCommand;
import src.controller.Controller;

public class ShowTaskCommand extends BaseCommand {
    public ShowTaskCommand(Controller controller) {
        super(controller, "show", "s", 1);
    }

    @Override
    public void execute(String[] args) {
        String projectName = (args.length > 0) ? args[0] : null;
        String taskName = (args.length > 1) ? args[1] : null;

        controller.showTask(projectName, taskName);
    }

    @Override 
    public String toString() {
        return "\t%s | %s <projectName> <taskName>\n".formatted(super.getKey(), super.getShortcut());
    }
}
