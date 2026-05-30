package src.commands.Task;

import java.time.LocalDateTime;
import java.util.Map;
import src.commands.BaseCommand;
import src.controller.Controller;
import src.utils.DateTimeUtil;

public class AddTaskCommand extends BaseCommand {
    private static final String NAME_FLAG = "n";
    private static final String DESCRIPTION_FLAG = "d";
    private static final String STATE_FLAG = "s";
    private static final String PRIORITY_FLAG = "p";
    private static final String DUE_DATE_FLAG = "t";

    public AddTaskCommand(Controller controller) {
        super(controller, "add", "a", 1);
    }

    @Override
    public void execute(String[] args) {
        final String projectName = getArg(args, 0);

        final Map<String, String> flags = getFlags(args);
        final String name = flags.get(NAME_FLAG);
        final String description = flags.get(DESCRIPTION_FLAG);
        final String state = flags.get(STATE_FLAG);
        final String priority = flags.get(PRIORITY_FLAG);
        final String dueDateFlagValue = flags.get(DUE_DATE_FLAG);
        final LocalDateTime dueDate = DateTimeUtil.parseDateTime(dueDateFlagValue);

        controller.addTask(projectName, name, description, state, priority,dueDate);
    }

    @Override 
    public String toString() {
        return """
            \t%s | %s <projectName> --%s <name> [--%s <desc>] [--%s <state>] [--%s <priority>] [--%s <date>]
            \t  --%s <name>        Set the task name
            \t  --%s <description> Set the task description (optional)
            \t  --%s <state>       Set the task state (optional)
            \t  --%s <priority>    Set the task priority (optional)
            \t  --%s <dueDate>     Set a due date (Format: %s)
            """.formatted(
                getKey(), getShortcut(), 
                NAME_FLAG, DESCRIPTION_FLAG, STATE_FLAG, PRIORITY_FLAG, DUE_DATE_FLAG, 
                NAME_FLAG, DESCRIPTION_FLAG, STATE_FLAG, PRIORITY_FLAG, DUE_DATE_FLAG,
                DateTimeUtil.FORMAT
            );
    }
}
