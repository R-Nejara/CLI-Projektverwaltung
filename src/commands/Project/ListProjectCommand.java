package src.commands.Project;

import src.commands.BaseCommand;
import src.controller.Controller;

public class ListProjectCommand extends BaseCommand {
    public ListProjectCommand(Controller controller) {
        super(controller, "list", "-l", 1);
    }

    @Override
    public void execute(String[] args) {
        String filter = (args.length > 0) ? args[0] : null;

        controller.listProjects(filter);
    }

    @Override 
    public String toString() {
        return "\t" + this.getKey().toUpperCase() + ": (project|-p) ("+ super.getKey() + "|" + super.getShortcut() + ") [filter]";
    }
}
