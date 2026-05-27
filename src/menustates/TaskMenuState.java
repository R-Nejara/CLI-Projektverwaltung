package src.menustates;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;
import src.controller.Controller;
import src.model.State;
import src.utils.DateTimeUtil;
import src.view.View;

public class TaskMenuState implements MenuState {
    private final Controller controller;
    private final View view;
    private final MenuState previousState;

    private Boolean skipHeader = false;

    public TaskMenuState(Controller controller, View view, MenuState previousState) {
        this.controller = controller;
        this.view = view;
        this.previousState = previousState;
    }

//-------------------------------------------------------------------------
// Section: Handle
//-------------------------------------------------------------------------
    
    @Override
    public MenuState handle() {
        String[] options = {"Add Task", "List Tasks", "Edit Task", "Delete Task", "Back to Main Menu"}; 

        Integer userSelection = view.readUserInput(
            options, 
            "Invalid option. Please select a valid option from the menu.", 
            !skipHeader
        );

        switch (userSelection) {
            case 1 -> { return addTask(); }
            case 2 -> { return listTasks(); }
            case 3 -> { return editTask(); }
            case 4 -> { return deleteTask(); }
            case 5 -> { return previousState; }
            default -> {
                view.printError("Invalid option. Please select a valid option from the menu.");
                return this;
            }
        }
    }

//-------------------------------------------------------------------------
// Section: Task states
//-------------------------------------------------------------------------

    private MenuState addTask() {
        String[] attributes = readAttributes(true, false, true, true, true, true);

        LocalDateTime dueDate = DateTimeUtil.parseDateTime(attributes[6]);
        controller.addTask(attributes[0], attributes[1], attributes[3], attributes[4], attributes[5], dueDate);
        return this;
    }

    private MenuState listTasks() {
        String[] attributes = readAttributes(false, false, false, false, false, false);

        String filterString = view.readUserInput(
            "Enter filter string (leave empty for no filter):", 
            null, 
            null, 
            true
        );

        controller.listTasks(attributes[0], filterString);
        this.skipHeader = true;
        return this;
    }

    private MenuState editTask() {
        String[] attributes = readAttributes(true, true, true, true, true, true);

        controller.editTask(
            attributes[0], // projectName
            attributes[1], // taskName
            attributes[2], // newName
            attributes[3], // newDescription
            attributes[4], // newState
            attributes[5], // newPriority
            DateTimeUtil.parseDateTime(attributes[6]) // newDueDate
        );

        this.skipHeader = true;
        return this;
    }

    private MenuState deleteTask() {
        String[] attributes = readAttributes(true, false, false, false, false, false);

        controller.removeTasks(attributes[0], Set.of(attributes[1]));
        this.skipHeader = true;
        return this;
    }

//-------------------------------------------------------------------------
// Section: private functions
//-------------------------------------------------------------------------

    private String[] readAttributes(Boolean askForTaskName, Boolean askForNewName,Boolean askForDescription, Boolean askForState, Boolean askForPriority, Boolean askForDueDate) {
        String projectName = view.readUserInput(
            "Enter project name:", 
            Pattern.compile(".+"),
            "Project name cannot be empty.",
            true
        );

        controller.listTasks(projectName, null);

        String taskName = askForTaskName ?view.readUserInput(
            "Enter task name:", 
            Pattern.compile(".+"),
            "Task name cannot be empty.",
            false
        ) : null;

        String newTaskName = askForNewName ? view.readUserInput(
            "Enter new task name (leave empty to keep current):", 
            Pattern.compile(".+"),
            "Task name cannot be empty.",
            true
        ) : null;

        String description = askForDescription ? view.readUserInput(
            "Enter new task description:", 
            null, 
            null, 
            true
        ) : null;

        String state = askForState ? view.readUserInput(
            "Enter new task state (" + Arrays.toString(State.values()) +"):", 
            null, 
            null, 
            true
        ) : null;

        String priority = askForPriority ? view.readUserInput(
            "Enter new task priority:", 
            null, 
            null, 
            true
        ) : null;

        String dueDateStr = askForDueDate ? view.readUserInput(
            "Enter task dueDate (" + DateTimeUtil.FORMAT + "):", 
            DateTimeUtil.FORMAT_REGEX, 
            "Invalid date format. Please enter the date in " + DateTimeUtil.FORMAT + " format.", 
            true
        ) : null;

        return new String[]{projectName, taskName, newTaskName, description, state, priority, dueDateStr};
    }
}
