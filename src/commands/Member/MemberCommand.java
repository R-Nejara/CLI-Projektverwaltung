package src.commands.Member;

import src.commands.BaseCommand;
import src.controller.Controller;

public class MemberCommand extends BaseCommand {
    public MemberCommand(Controller controller) {
        super(controller, "member", "m", 0);
        registerSubCommand(new AddMemberCommand(controller));
        registerSubCommand(new ListMemberCommand(controller));
        registerSubCommand(new EditMemberCommand(controller));
        registerSubCommand(new RemoveMemberCommand(controller));
        registerSubCommand(new HelpMemberCommand(controller, this));
    }
}
