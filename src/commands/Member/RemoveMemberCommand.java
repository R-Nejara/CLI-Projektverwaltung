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
        String projectName = super.getArg(args, 0);
        String taskName = super.getArg(args, 1);
        String[] memberNames = super.getSubArgs(args, 2);

        controller.removeAssignees(projectName, taskName, Set.of(memberNames));
    }

    @Override 
    public String toString() {
        return "\t%s | %s <projectName> <taskName> <memberName>...\n".formatted(super.getKey(), super.getShortcut());
    }
}
