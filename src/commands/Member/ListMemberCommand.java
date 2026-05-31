package src.commands.Member;

import src.commands.BaseCommand;
import src.controller.Controller;

public class ListMemberCommand extends BaseCommand {
    public ListMemberCommand(Controller controller) {
        super(controller, "list", "l", 1);
    }

    @Override
    public void execute(String[] args) {
        final String projectName = getArg(args, 0);
        final String taskName = getArg(args, 1);
        final String filter = getArg(args, 2);

        controller.listMembers(projectName, taskName, filter);
    }

    @Override 
    public String toString() {
        return "\t%s | %s  <projectName> <taskName> [<filter>]\n".formatted(getKey(), getShortcut());
    }
}
