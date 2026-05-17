package src.commands.Project;

import src.commands.BaseCommand;
import src.controller.Controller;

public class ShowProjectCommand extends BaseCommand {
    public ShowProjectCommand(Controller controller) {
        super(controller, "show", "s", 1);
    }

    @Override
    public void execute(String[] args) {
        String name = super.getArg(args, 0);

        controller.showProject(name);
    }

    @Override 
    public String toString() {
        return "\t%s | %s <name>\n".formatted(super.getKey(), super.getShortcut());
    }
}
