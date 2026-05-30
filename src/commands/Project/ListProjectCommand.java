package src.commands.Project;

import src.commands.BaseCommand;
import src.controller.Controller;

public class ListProjectCommand extends BaseCommand {
    public ListProjectCommand(Controller controller) {
        super(controller, "list", "l", 1);
    }

    @Override
    public void execute(String[] args) {
        final String filter = getArg(args, 0);

        controller.listProjects(filter);
    }

    @Override 
    public String toString() {
        return "\t%s | %s [<filter>]\n".formatted(getKey(), getShortcut());
    }
}
