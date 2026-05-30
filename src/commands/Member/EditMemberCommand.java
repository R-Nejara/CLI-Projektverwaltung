package src.commands.Member;

import java.util.Map;
import src.commands.BaseCommand;
import src.controller.Controller;

public class EditMemberCommand extends BaseCommand {
    private static final String NAME_FLAG = "n";
    private static final String ROLE_FLAG = "r";

    public EditMemberCommand(Controller controller) {
        super(controller, "edit", "e", 1);
    }

    @Override
    public void execute(String[] args) {
        final String projectName = getArg(args, 0);
        final String taskName = getArg(args, 1);
        final String memberName = getArg(args, 2);

        final Map<String, String> flags = getFlags(args);
        final String newName = flags.get(NAME_FLAG);
        final String role = flags.get(ROLE_FLAG);

        controller.editAssignee(projectName, taskName, memberName, newName, role);
    }

    @Override 
    public String toString() {
        return """
            \t%s | %s <projectName> <taskName> <memberName> [--%s <newName>] [--%s <role>]
            \t  --%s <newName>     Update the member name (optional)
            \t  --%s <role>        Update the member role (optional)
            """.formatted(
                getKey(), getShortcut(),
                NAME_FLAG, ROLE_FLAG,
                NAME_FLAG, ROLE_FLAG
            );
    }
}
