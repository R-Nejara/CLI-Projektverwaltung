package src.commands.Project;

import java.time.LocalDateTime;
import java.util.Map;
import src.commands.BaseCommand;
import src.controller.Controller;
import src.utils.DateTimeUtil;

public class EditProjectCommand extends BaseCommand {
    private static final String NAME_FLAG = "n";
    private static final String DESCRIPTION_FLAG = "d";
    private static final String DUE_DATE_FLAG = "t";

    public EditProjectCommand(Controller controller) {
        super(controller, "edit", "e", 1);
    }

    @Override
    public void execute(String[] args) {
        final String name = getArg(args, 0);

        final Map<String, String> flags = getFlags(args);
        final String newName = flags.get(NAME_FLAG);
        final String description = flags.get(DESCRIPTION_FLAG);
        final String dueDateFlagValue = flags.get(DUE_DATE_FLAG);
        final LocalDateTime dueDate = DateTimeUtil.parseDateTime(dueDateFlagValue);

        controller.editProject(name, newName, description, dueDate);
    }

    @Override 
    public String toString() {
        return """
            \t%s | %s <name> [--%s <newName>] [--%s <desc>] [--%s <date>]
            \t  --%s <name>        Update the project name (optional)
            \t  --%s <description> Update the project description (optional)
            \t  --%s <dueDate>     Update the due date (Format: %s)
            """.formatted(
                getKey(), getShortcut(),
                NAME_FLAG, DESCRIPTION_FLAG, DUE_DATE_FLAG,
                NAME_FLAG, DESCRIPTION_FLAG, DUE_DATE_FLAG,
                DateTimeUtil.FORMAT
            );
    }
}
