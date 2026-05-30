package src.commands.Project;

import java.util.Set;
import src.commands.BaseCommand;
import src.controller.Controller;

public class RemoveProjectCommand extends BaseCommand {
    public RemoveProjectCommand(Controller controller) {
        super(controller, "remove", "r", 1);
    }

    @Override
    public void execute(String[] args) {
        controller.removeProjects(Set.of(args));
    }

    @Override 
    public String toString() {
        return "\t%s | %s <name>...\n".formatted(getKey(), getShortcut());
    }
}
