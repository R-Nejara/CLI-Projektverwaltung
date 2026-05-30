package src.commands.Member;

import java.util.Map;
import src.commands.BaseCommand;
import src.controller.Controller;

public class AddMemberCommand extends BaseCommand {
    private static final String NAME_FLAG = "n";
    private static final String ROLE_FLAG = "r";

    public AddMemberCommand(Controller controller) {
        super(controller, "add", "a", 1);
    }

    @Override
    public void execute(String[] args) {
        final String projectName = getArg(args, 0);
        final String taskName = getArg(args, 1);

        final Map<String, String> flags = getFlags(args);
        final String name = flags.get(NAME_FLAG);
        final String role = flags.get(ROLE_FLAG);

        controller.addAssignee(projectName, taskName, name, role);
    }

    @Override 
    public String toString() {
        return """
            \t%s | %s <projectName> <taskName> --%s <name> [--%s <role>]
            \t  --%s <name>        Set the member name
            \t  --%s <role>        Set the member role (optional)
            """.formatted(
                getKey(), getShortcut(),
                NAME_FLAG, ROLE_FLAG,
                NAME_FLAG, ROLE_FLAG
            );
    }
}
