package src.commands.Task;

import java.util.Set;
import src.commands.BaseCommand;
import src.controller.Controller;

public class RemoveTaskCommand extends BaseCommand {
    public RemoveTaskCommand(Controller controller) {
        super(controller, "remove", "r", 1);
    }

    @Override
    public void execute(String[] args) {
        String projectName = (args.length > 0) ? args[0] : null;
        String[] taskNames = super.getSubArgs(args);

        controller.removeTasks(projectName, Set.of(taskNames));
    }

    @Override 
    public String toString() {
        return "\t%s | %s <projectName> <taskName>...\n".formatted(super.getKey(), super.getShortcut());
    }
}
