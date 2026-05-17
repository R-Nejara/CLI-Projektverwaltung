package src.commands.Project;

import java.time.LocalDateTime;
import java.util.Map;
import src.commands.BaseCommand;
import src.controller.Controller;

public class EditProjectCommand extends BaseCommand {
    public EditProjectCommand(Controller controller) {
        super(controller, "edit", "e", 1);
    }

    @Override
    public void execute(String[] args) {
        final String name = super.getArg(args, 0);
        String newName = null, description = null;
        LocalDateTime dueDate = null;

        Map<String, String> flags = super.getFlags(args);

        for (Map.Entry<String, String> entry : flags.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
                case "n" -> newName = value;
                case "d" -> description = value;
                case "t" -> dueDate = super.parseDateTime(value);
                default -> {}
            }
        }

        controller.editProject(name, newName, description, dueDate);
    }

    @Override 
    public String toString() {
        return """
            \t%s | %s <name> [--n <newName>] [--d <desc>] [--t <date>]
            \t  --n <name>        Update the project name (optional)
            \t  --d <description> Update the project description (optional)
            \t  --t <dueDate>     Update the due date (Format: dd.MM.yyyy [HH:mm])
            """.formatted(super.getKey(), super.getShortcut());
    }
}
