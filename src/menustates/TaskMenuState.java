package src.menustates;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import src.controller.Controller;
import src.model.Priority;
import src.model.State;
import src.utils.DateTimeUtil;
import src.view.View;

public class TaskMenuState implements MenuState {
    private final Controller controller;
    private final View view;
    private final Map<String, Supplier<MenuState>> menuActions = new LinkedHashMap<>();

    private record TaskAttributes(
        String projectName, 
        String taskName,
        String newTaskName,
        String description, 
        String state,
        String priority, 
        String dueDate
    ) {}

    public TaskMenuState(Controller controller, View view, MenuState previousState) {
        this.controller = controller;
        this.view = view;

        menuActions.put("Add Task", () -> addTask());
        menuActions.put("List Tasks", () -> listTasks());
        menuActions.put("Edit Task", () -> editTask());
        menuActions.put("Delete Task", () -> deleteTask());
        menuActions.put("Back to Main Menu", () -> previousState);
    }

//-------------------------------------------------------------------------
// Section: Handle
//-------------------------------------------------------------------------
    
    @Override
    public MenuState handle() {
        final String[] options = menuActions.keySet().toArray(String[]::new);
        final String errorMsg = "Invalid option. Please select a valid option from the menu.";
        final int selection = view.readUserInput(options, errorMsg, true);

        if (selection < 1 || selection > options.length) { return this; }

        final String selectedKey = options[selection - 1];

        return menuActions.get(selectedKey).get();
    }

//-------------------------------------------------------------------------
// Section: Task states
//-------------------------------------------------------------------------

    private MenuState addTask() {
        TaskAttributes attributes = readAttributes(true, false, true, true, true, true, true);

        LocalDateTime dueDate = DateTimeUtil.parseDateTime(attributes.dueDate());
        controller.addTask(attributes.projectName(), attributes.taskName(), attributes.description(), attributes.state(), attributes.priority(), dueDate);
        return this;
    }

    private MenuState listTasks() {
        TaskAttributes attributes = readAttributes(false, false, false, false, false, false, true);

        String filterString = view.readUserInput("Enter filter string (leave empty for no filter):", null, null, true);

        controller.listTasks(attributes.projectName(), filterString);
        return this;
    }

    private MenuState editTask() {
        TaskAttributes attributes = readAttributes(true, true, true, true, true, true, true);

        controller.editTask(
            attributes.projectName(),
            attributes.taskName(),
            attributes.newTaskName(),
            attributes.description(),
            attributes.state(),
            attributes.priority(),
            DateTimeUtil.parseDateTime(attributes.dueDate())
        );

        return this;
    }

    private MenuState deleteTask() {
        TaskAttributes attributes = readAttributes(true, false, false, false, false, false, true);

        controller.removeTasks(attributes.projectName(), Set.of(attributes.taskName()));
        return this;
    }

//-------------------------------------------------------------------------
// Section: private functions
//-------------------------------------------------------------------------

    private TaskAttributes readAttributes(boolean askForTaskName, boolean askForNewName,boolean askForDescription, boolean askForState, boolean askForPriority, boolean askForDueDate, boolean skipHeader) {
        final boolean shouldClear = !skipHeader;

        controller.listProjects(null);
        String projectName = view.readUserInput(
            "Enter project name:",  Pattern.compile(".+"), "Project name cannot be empty.", shouldClear
        );

        if (askForTaskName) {
            controller.listTasks(projectName, null);
        }

        String taskName = askForTaskName ?view.readUserInput(
            "Enter task name:", Pattern.compile(".+"),"Task name cannot be empty.", shouldClear
        ) : null;

        String newTaskName = askForNewName ? view.readUserInput(
            "Enter new task name (leave empty to keep current):", Pattern.compile("^([a-zA-Z][^|]*|)$"), "Task name must start with a letter and cannot contain a pipe character.", shouldClear
        ) : null;

        String description = askForDescription ? view.readUserInput(
            "Enter new task description:", null, null, shouldClear
        ) : null;

        String state = askForState ? view.readUserInput(
            "Enter new task state (" + Arrays.toString(State.values()) +"):", null, null, shouldClear
        ) : null;

        String priority = askForPriority ? view.readUserInput(
            "Enter new task priority (" + Arrays.toString(Priority.values()) +"):", null, null, shouldClear
        ) : null;

        String dueDateStr = askForDueDate ? view.readUserInput(
            "Enter task dueDate (" + DateTimeUtil.FORMAT + "):", DateTimeUtil.FORMAT_REGEX, "Invalid date format. Please enter the date in " + DateTimeUtil.FORMAT + " format.", shouldClear
        ) : null;

        return new TaskAttributes(projectName, taskName, newTaskName, description, state, priority, dueDateStr);
    }
}
