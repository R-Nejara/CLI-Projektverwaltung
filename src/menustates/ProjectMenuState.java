package src.menustates;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import src.controller.Controller;
import src.utils.DateTimeUtil;
import src.view.View;

public class ProjectMenuState implements MenuState {
    private final Controller controller;
    private final View view;
    private final Map<String, Supplier<MenuState>> menuActions = new LinkedHashMap<>();

    private record ProjectAttributes(
        String projectName, 
        String newProjectName, 
        String description, 
        String dueDate
    ) {}

    public ProjectMenuState(Controller controller, View view, MenuState previousState) {
        this.controller = controller;
        this.view = view;

        menuActions.put("Add Project", () -> addProject());
        menuActions.put("List Projects", () -> listProjects());
        menuActions.put("Show Project", () -> showProject());
        menuActions.put("Edit Project", () -> editProject());
        menuActions.put("Delete Project", () -> deleteProject());
        menuActions.put("Back to Main Menu", () -> previousState);
    }

//-------------------------------------------------------------------------
// Section: Handle
//-------------------------------------------------------------------------
    
    @Override
    public MenuState handle() {
        final String[] options = menuActions.keySet().toArray(String[]::new);
        final String errorMsg = "Invalid option. Please select a valid option from the menu.";
        final Integer selection = view.readUserInput(options, errorMsg, true);

        if (selection == null || selection < 1 || selection > options.length) { return this; }

        final String selectedKey = options[selection - 1];

        return menuActions.get(selectedKey).get();
    }

//-------------------------------------------------------------------------
// Section: Project states
//-------------------------------------------------------------------------

    private MenuState addProject() {
        ProjectAttributes attributes = readAttributes(false, true, true, true);

        controller.addProject(attributes.projectName(), attributes.description(), DateTimeUtil.parseDateTime(attributes.dueDate()));
        return this;
    }

    private MenuState editProject() {
        ProjectAttributes attributes = readAttributes(true, true, true, true);

        controller.editProject(attributes.projectName(), attributes.newProjectName(), attributes.description(), DateTimeUtil.parseDateTime(attributes.dueDate()));
        return this;
    }

    private MenuState listProjects() {
        String filter = view.readUserInput("Enter a filter or press enter:", null, null, true);
        
        controller.listProjects(filter);
        view.readUserInput("Press enter to continue...", null, null, false);
        return this;
    }

    private MenuState showProject() {
        ProjectAttributes attributes = readAttributes(false, false, false, true);

        controller.showProject(attributes.projectName());
        return this;
    }

    private MenuState deleteProject() {
        ProjectAttributes attributes = readAttributes(false, false, false, true);

        controller.removeProjects(Set.of(attributes.projectName()));
        return this;
    }

//-------------------------------------------------------------------------
// Section: private functions
//-------------------------------------------------------------------------

private ProjectAttributes readAttributes(boolean askForNewName, boolean askForDescription, boolean askForDueDate, boolean skipHeader) {
    final Pattern namePattern = Pattern.compile("^([a-zA-Z][^|]*|)$");
    final String nameError = "Project name must start with a letter and cannot contain a pipe character.";
    final boolean shouldClear = !skipHeader;

    controller.listProjects(null);

    final String projectName = view.readUserInput("Enter project name:", namePattern, nameError, shouldClear);

    final String newProjectName = askForNewName 
        ? view.readUserInput("Enter new project name:", namePattern, nameError, shouldClear) 
        : null;

    final String description = askForDescription 
        ? view.readUserInput("Enter new project description:", null, null, shouldClear) 
        : null;

    final String dueDateStr = askForDueDate 
        ? view.readUserInput(
            "Enter project dueDate (%s):".formatted(DateTimeUtil.FORMAT), 
            DateTimeUtil.FORMAT_REGEX, 
            "Invalid date format. Please enter the date in %s format.".formatted(DateTimeUtil.FORMAT), 
            shouldClear
          ) 
        : null;

    return new ProjectAttributes(
        projectName,
        (newProjectName != null && !newProjectName.isBlank()) ? newProjectName : null,
        (description != null && !description.isBlank()) ? description : null,
        (dueDateStr != null && !dueDateStr.isBlank()) ? dueDateStr : null
    );
}
}
