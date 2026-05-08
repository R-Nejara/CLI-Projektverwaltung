package src.commands.Project;

import src.commands.BaseCommand;
import src.commands.ExampleCommand;

public class AddProjectCommand extends BaseCommand {
    public AddProjectCommand() {
        super("add", "-a", 1);
        super.registerSubCommand(new ExampleCommand());
    }

    @Override
    public void execute(String[] args) {
        String name = "<empty>";
        String description = "<empty>";

        if (args.length >= 1) {
            name = args[0];
        } else {
            System.err.println("ERROR: missing name");
        }

        if (args.length > 1) {
            description = args[1];
        }

        System.out.printf("add_project(name: %s, desciption: %s)\n", name, description);
    }

    @Override 
    public String toString() {
        return "\t" + this.getKey().toUpperCase() + ": (project|-p) (add|-a) <name> [description]";
    }
}
