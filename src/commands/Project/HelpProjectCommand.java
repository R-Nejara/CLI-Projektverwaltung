package src.commands.Project;

import src.commands.BaseCommand;
import src.commands.Command;
import src.controller.Controller;

public class HelpProjectCommand extends BaseCommand {
    private final Command command;
    public HelpProjectCommand(Controller controller, Command command) {
        super(controller, "help", "h", 1);
        this.command = command;
    }

    @Override
    public void execute(String[] args) {
        System.out.println(command.toString());
    }

    @Override 
    public String toString() {
        return "\t%s | %s\n".formatted(super.getKey(), super.getShortcut());
    }
}
