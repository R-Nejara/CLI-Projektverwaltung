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
        String projectName = (args.length > 0) ? args[0] : null;
        String taskName = (args.length > 1) ? args[1] : null;
        String[] memberNames = super.getSubArgs(args, 2);

        controller.removeAssignees(projectName, taskName, Set.of(memberNames));
    }

    @Override 
    public String toString() {
        return "\t%s | %s <projectName> <taskName> <memberName>...\n".formatted(super.getKey(), super.getShortcut());
    }
}
