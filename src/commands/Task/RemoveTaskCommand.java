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
        final String projectName = getArg(args, 0);

        controller.removeTasks(projectName, Set.of(getSubArgs(args)));
    }

    @Override 
    public String toString() {
        return "\t%s | %s <projectName> <taskName>...\n".formatted(getKey(), getShortcut());
    }
}
