package src.commands.Task;

import java.util.Map;
import src.commands.BaseCommand;
import src.controller.Controller;

public class AddTaskCommand extends BaseCommand {
    public AddTaskCommand(Controller controller) {
        super(controller, "add", "a", 1);
    }

    @Override
    public void execute(String[] args) {
        final String projectName = (args.length > 0) ? args[0] : null;
        String name = null, description = null, state = null;

        Map<String, String> flags = super.getFlags(args);

        for (Map.Entry<String, String> entry : flags.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
                case "n" -> name = value;
                case "d" -> description = value;
                case "s" -> state = value; 
                default -> {}
            }
        }
        controller.addTask(projectName, name, description, state);
    }

    @Override 
    public String toString() {
        return """
            \t%s | %s <projectName> [--n <name>] [--d <desc>] [--t <state>]
            \t  --n <name>        Set the task name
            \t  --d <description> Set the task description (optional)
            \t  --s <state>       Set the task state (optional)
            """.formatted(super.getKey(), super.getShortcut());
    }
}
