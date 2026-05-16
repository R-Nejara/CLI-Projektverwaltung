package src.commands.Member;

import src.commands.BaseCommand;
import src.controller.Controller;

public class MemberCommand extends BaseCommand {
    public MemberCommand(Controller controller) {
        super(controller, "member", "m", 0);
        super.registerSubCommand(new AddMemberCommand(controller));
        super.registerSubCommand(new EditMemberCommand(controller));
        super.registerSubCommand(new RemoveMemberCommand(controller));
        super.registerSubCommand(new HelpMemberCommand(controller, this));
    }
}
