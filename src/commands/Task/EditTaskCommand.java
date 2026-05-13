package src.commands.Task;

import java.util.Map;
import src.commands.BaseCommand;
import src.controller.Controller;

public class EditTaskCommand extends BaseCommand {
    public EditTaskCommand(Controller controller) {
        super(controller, "edit", "e", 1);
    }

    @Override
    public void execute(String[] args) {
        final String taskName = (args.length > 0) ? args[0] : null;
        String newName = null, description = null, state = null;

        Map<String, String> flags = super.getFlags(args);

        for (Map.Entry<String, String> entry : flags.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
                case "n" -> newName = value;
                case "d" -> description = value;
                case "s" -> state = value;
                default -> {}
            }
        }

        controller.editTask(taskName, newName, description, state);
    }

    @Override 
    public String toString() {
        return """
            \t%s | %s <projectName> [--n <name>] [--d <desc>] [--s <state>]
            \t  --n <name>        Update the task name (optional)
            \t  --d <description> Update the task description (optional)
            \t  --s <state>       Update the task state (optional)
            """.formatted(super.getKey(), super.getShortcut());
    }
}
