package src.commands.Project;

import src.commands.BaseCommand;
import src.controller.Controller;

public class RemoveProjectCommand extends BaseCommand {
    public RemoveProjectCommand(Controller controller) {
        super(controller, "remove", "r", 1);
    }

    @Override
    public void execute(String[] args) {
        controller.removeProjects(args);
    }

    @Override 
    public String toString() {
        return "\t%s | %s <name>...\n".formatted(super.getKey(), super.getShortcut());
    }
}
