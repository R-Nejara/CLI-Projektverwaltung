package src.commands.Member;

import java.util.Set;
import src.commands.BaseCommand;
import src.controller.Controller;

public class RemoveMemberCommand extends BaseCommand {
    public RemoveMemberCommand(Controller controller) {
        super(controller, "remove", "r", 1);
    }

    @Override
    public void execute(String[] args) {
        final String projectName = getArg(args, 0);
        final String taskName = getArg(args, 1);
        final String[] memberNames = getSubArgs(args, 2);

        controller.removeAssignees(projectName, taskName, Set.of(memberNames));
    }

    @Override 
    public String toString() {
        return "\t%s | %s <projectName> <taskName> <memberName>...\n".formatted(getKey(), getShortcut());
    }
}
