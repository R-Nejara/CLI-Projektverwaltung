package src.commands.Task;

import java.time.LocalDateTime;
import java.util.Map;
import src.commands.BaseCommand;
import src.controller.Controller;

public class EditTaskCommand extends BaseCommand {
    public EditTaskCommand(Controller controller) {
        super(controller, "edit", "e", 1);
    }

    @Override
    public void execute(String[] args) {
        final String projectName = super.getArg(args, 0);
        final String taskName = super.getArg(args, 1);
        String newName = null, description = null, state = null, priority = null;
        LocalDateTime dueDate = null;

        Map<String, String> flags = super.getFlags(args);

        for (Map.Entry<String, String> entry : flags.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
                case "n" -> newName = value;
                case "d" -> description = value;
                case "s" -> state = value;
                case "p" -> priority = value;
                case "t" -> dueDate = super.parseDateTime(value); 
                default -> {}
            }
        }

        controller.editTask(projectName, taskName, newName, description, state, priority, dueDate);
    }

    @Override 
    public String toString() {
        return """
            \t%s | %s <projectName> <taskName> [--n <newName>] [--d <desc>] [--s <state>] [--p <priority>] [--t <date>]
            \t  --n <newName>     Update the task name (optional)
            \t  --d <description> Update the task description (optional)
            \t  --s <state>       Update the task state (optional)
            \t  --p <priority>    Update the task priority (optional)
            \t  --t <dueDate>     Set a due date (Format: dd.MM.yyyy [HH:mm])
            """.formatted(super.getKey(), super.getShortcut());
    }
}
