package src.commands.Project;

import src.commands.BaseCommand;
import src.controller.Controller;

public class EditProjectCommand extends BaseCommand {
    public EditProjectCommand(Controller controller) {
        super(controller, "edit", "-e", 1);
    }

    @Override
    public void execute(String[] args) {
        String name = (args.length > 0) ? args[0] : null;
        //TODO flags
        controller.editProject(name, null, null, null);
    }

    @Override 
    public String toString() {
        return "\t" + super.getKey() + " | " + super.getShortcut() + " <name> [-n <newName>] [-d <description>] [-t <dueDate>]";
    }
}
