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
                case "t" -> dueDate = super.parseDateTime(value); 
                default -> {}
            }
        }
        controller.addProject(name, description, dueDate);
    }

    @Override 
    public String toString() {
        return """
            \t%s | %s --n <name> [--d <desc>] [--t <date>]
            \t  --n <name>        Set the project name
            \t  --d <description> Set the project description (optional)
            \t  --t <dueDate>     Set a due date (Format: dd.MM.yyyy [HH:mm])
            """.formatted(super.getKey(), super.getShortcut());
    }
}
