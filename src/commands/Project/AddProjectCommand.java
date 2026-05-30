package src.commands.Project;

import java.time.LocalDateTime;
import java.util.Map;
import src.commands.BaseCommand;
import src.controller.Controller;
import src.utils.DateTimeUtil;

public class AddProjectCommand extends BaseCommand {
    private static final String NAME_FLAG = "n";
    private static final String DESCRIPTION_FLAG = "d";
    private static final String DUE_DATE_FLAG = "t";

    public AddProjectCommand(Controller controller) {
        super(controller, "add", "a", 1);
    }

    @Override
    public void execute(String[] args) {
        final Map<String, String> flags = getFlags(args);
        final String name = flags.get(NAME_FLAG);
        final String description = flags.get(DESCRIPTION_FLAG);
        final String dueDateFlagValue = flags.get(DUE_DATE_FLAG);
        final LocalDateTime dueDate = DateTimeUtil.parseDateTime(dueDateFlagValue);

        controller.addProject(name, description, dueDate);
    }

    @Override 
    public String toString() {
        return """
            \t%s | %s --%s <name> [--%s <desc>] [--%s <date>]
            \t  --%s <name>        Set the project name
            \t  --%s <description> Set the project description (optional)
            \t  --%s <dueDate>     Set a due date (Format: dd.MM.yyyy [HH:mm])
            """.formatted(
                getKey(), getShortcut(),
                NAME_FLAG, DESCRIPTION_FLAG, DUE_DATE_FLAG,
                NAME_FLAG, DESCRIPTION_FLAG, DUE_DATE_FLAG
            );
    }
}
