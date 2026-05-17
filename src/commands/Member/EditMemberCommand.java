package src.commands.Member;

import java.util.Map;
import src.commands.BaseCommand;
import src.controller.Controller;

public class EditMemberCommand extends BaseCommand {
    public EditMemberCommand(Controller controller) {
        super(controller, "edit", "e", 1);
    }

    @Override
    public void execute(String[] args) {
        final String projectName = super.getArg(args, 0);
        final String taskName = super.getArg(args, 1);
        final String memberName = super.getArg(args, 2);
        String newName = null, role = null;

        Map<String, String> flags = super.getFlags(args);

        for (Map.Entry<String, String> entry : flags.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
                case "n" -> newName = value;
                case "r" -> role = value;
                default -> {}
            }
        }

        controller.editAssignee(projectName, taskName, memberName, newName, role);
    }

    @Override 
    public String toString() {
        return """
            \t%s | %s <projectName> <taskName> <memberName> [--n <newName>] [--r <role>]
            \t  --n <newName>     Update the member name (optional)
            \t  --d <role>        Update the member role (optional)
            """.formatted(super.getKey(), super.getShortcut());
    }
}
