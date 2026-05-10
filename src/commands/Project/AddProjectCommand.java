package src.commands.Project;

import java.time.LocalDateTime;
import src.commands.BaseCommand;
import src.controller.Controller;

public class AddProjectCommand extends BaseCommand {
    public AddProjectCommand(Controller controller) {
        super(controller, "add", "-a", 1);
    }

    @Override
    public void execute(String[] args) {
        String name = (args.length > 0) ? args[0] : null;
        String description = (args.length > 1) ? args[1] : null;
        LocalDateTime dueDate = null;

        controller.addProject(name, description, dueDate);
    }

    @Override 
    public String toString() {
        return "\t" + super.getKey() + " | " + super.getShortcut() + " <name> [<description>]";
    }
}
