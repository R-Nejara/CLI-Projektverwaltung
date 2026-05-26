package src.menustates;

import java.util.Set;
import java.util.regex.Pattern;
import src.controller.Controller;
import src.utils.DateTimeUtil;
import src.view.View;

public class ProjectMenuState implements MenuState {
    private final Controller controller;
    private final View view;
    private final MenuState previousState;

    private Boolean skipHeader = false;

    public ProjectMenuState(Controller controller, View view, MenuState previousState) {
        this.controller = controller;
        this.view = view;
        this.previousState = previousState;
    }

//-------------------------------------------------------------------------
// Section: Handle
//-------------------------------------------------------------------------
    
    @Override
    public MenuState handle() {
        Integer userSelection;
        String[] options = {"Add Project", "List Projects", "Show Project", "Edit Project", "Delete Project", "Back to Main Menu"}; 

        userSelection = view.readUserInput(
            options, 
            "Invalid option. Please select a valid option from the menu.", 
            !skipHeader
        );

        switch (userSelection) {
            case 1 -> { return addProject(); }
            case 2 -> { return listProjects(); }
            case 3 -> { return showProject(); }
            case 4 -> { return editProject(); }
            case 5 -> { return deleteProject(); }
            case 6 -> { return previousState; }
            default -> {
                view.printError("Invalid option. Please select a valid option from the menu.");
                return this;
            }
        }
    }

//-------------------------------------------------------------------------
// Section: Project states
//-------------------------------------------------------------------------

    private MenuState addProject() {
        String[] attributes = readAttributes(false, true, true);

        controller.addProject(attributes[0], attributes[2], DateTimeUtil.parseDateTime(attributes[3]));
        this.skipHeader = true;
        return this;
    }

    private MenuState editProject() {
        controller.listProjects(null);
        String[] attributes = readAttributes(true, true, true);

        controller.editProject(attributes[0], attributes[1], attributes[2], DateTimeUtil.parseDateTime(attributes[3]));
        this.skipHeader = true;
        return this;
    }

    private MenuState listProjects() {
        String filter = view.readUserInput(
            "Enter a filter or press enter:", 
            null, 
            null, 
            true
        );
        
        controller.listProjects(filter);
        this.skipHeader = true;
        return this;
    }

    private MenuState showProject() {
        controller.listProjects(null);
        String[] attributes = readAttributes(false, false, false);

        controller.showProject(attributes[0]);
        this.skipHeader = true;
        return this;
    }

    private MenuState deleteProject() {
        controller.listProjects(null);
        String[] attributes = readAttributes(false, false, false);

        controller.removeProjects(Set.of(attributes[0]));
        this.skipHeader = true;
        return this;
    }

//-------------------------------------------------------------------------
// Section: private functions
//-------------------------------------------------------------------------

    private String[] readAttributes(Boolean askForNewName, Boolean askForDescription, Boolean askForDueDate) {
        String projectName = view.readUserInput(
            "Enter project name:", 
            Pattern.compile(".+"),
            "Project name cannot be empty.",
            true
        );

        String newProjectName = askForNewName ? view.readUserInput(
            "Enter new project name:", 
            Pattern.compile(".+"),
            "Project name cannot be empty.",
            true
        ) : null;

        String description = askForDescription ? view.readUserInput(
            "Enter new project description:", 
            null, 
            null, 
            true
        ) : null;

        String dueDateStr = askForDueDate ? view.readUserInput(
            "Enter project dueDate (" + DateTimeUtil.FORMAT + "):", 
            DateTimeUtil.FORMAT_REGEX, 
            "Invalid date format. Please enter the date in " + DateTimeUtil.FORMAT + " format.", 
            true
        ) : null;

        return new String[]{projectName, newProjectName, description, dueDateStr};
    }
}
