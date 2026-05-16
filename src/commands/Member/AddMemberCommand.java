package src.commands.Member;

import java.util.Map;
import src.commands.BaseCommand;
import src.controller.Controller;

public class AddMemberCommand extends BaseCommand {
    public AddMemberCommand(Controller controller) {
        super(controller, "add", "a", 1);
    }

    @Override
    public void execute(String[] args) {
        final String projectName = (args.length > 0) ? args[0] : null;
        final String taskName = (args.length > 1) ? args[1] : null;
        String name = null, role = null;

        Map<String, String> flags = super.getFlags(args);

        for (Map.Entry<String, String> entry : flags.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
                case "n" -> name = value;
                case "r" -> role = value;
                default -> {}
            }
        }
        controller.addAssignee(projectName, taskName, name, role);
    }

    @Override 
    public String toString() {
        return """
            \t%s | %s <projectName> <taskName> --n <name> [--r <role>]
            \t  --n <name>        Set the member name
            \t  --r <role>        Set the member role (optional)
            """.formatted(super.getKey(), super.getShortcut());
    }
}
