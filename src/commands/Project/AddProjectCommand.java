package src.commands.Project;

import java.time.LocalDateTime;
import java.util.Map;
import src.commands.BaseCommand;
import src.controller.Controller;

public class AddProjectCommand extends BaseCommand {
    public AddProjectCommand(Controller controller) {
        super(controller, "add", "a", 1);
    }

    @Override
    public void execute(String[] args) {
        String name = null, description = null;
        LocalDateTime dueDate = null;

        Map<String, String> flags = super.getFlags(args);

        for (Map.Entry<String, String> entry : flags.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
                case "n" -> name = value;
                case "d" -> description = value;
                case "t" -> dueDate = LocalDateTime.now(); //TODO 
                default -> {}
            }
        }

        controller.addProject(name, description, dueDate);
    }

    @Override 
    public String toString() {
        return "\t" + super.getKey() + " | " + super.getShortcut() + " [--n <name>] [--d <description>] [--t <dueDate>]";
    }
}
