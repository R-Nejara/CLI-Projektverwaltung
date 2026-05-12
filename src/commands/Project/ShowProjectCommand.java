package src.commands.Project;

import src.commands.BaseCommand;
import src.controller.Controller;

public class ShowProjectCommand extends BaseCommand {
    public ShowProjectCommand(Controller controller) {
        super(controller, "show", "-s", 1);
    }

    @Override
    public void execute(String[] args) {
        String name = (args.length > 0) ? args[0] : null;

        controller.showProject(name);
    }

    @Override 
    public String toString() {
        return "\t" + super.getKey() + " | " + super.getShortcut() + " <name>";
    }
}
