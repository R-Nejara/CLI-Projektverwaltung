package src.menustates;

import java.time.LocalDateTime;
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
        String[] options = {"Add Project", "List Projects", "Show Project", "Delete Project", "Back to Main Menu"}; 

        userSelection = view.readUserInput(
            options, 
            "Invalid option. Please select a valid option from the menu.", 
            !skipHeader
        );

        switch (userSelection) {
            case 1 -> { return addProject(); }
            case 2 -> { return listProjects(); }
            case 3 -> { return showProject(); }
            case 4 -> { return deleteProject(); }
            case 5 -> { return previousState; }
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
        String projectName = view.readUserInput(
            "Enter project name:", 
            Pattern.compile(".+"),
            "Project name cannot be empty.",
            true
        );

        String projectDescription = view.readUserInput(
            "Enter project description:", 
            null, 
            null, 
            true
        );

        String dueDateStr = view.readUserInput(
            "Enter project dueDate (" + DateTimeUtil.FORMAT + "):", 
            DateTimeUtil.FORMAT_REGEX, 
            "Invalid date format. Please enter the date in " + DateTimeUtil.FORMAT + " format.", 
            true
        );

        LocalDateTime dueDate = DateTimeUtil.parseDateTime(dueDateStr);
        controller.addProject(projectName, projectDescription, dueDate);
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
        String projectName = view.readUserInput(
            "Enter project name:", 
            Pattern.compile(".+"), 
            "Project name cannot be empty.", 
            false
        );

        controller.showProject(projectName);
        this.skipHeader = true;
        return this;
    }

    private MenuState deleteProject() {
        controller.listProjects(null);
        String projectName = view.readUserInput(
            "Enter project name:", 
            Pattern.compile(".+"), 
            "Project name cannot be empty.", 
            false
        );

        controller.removeProjects(Set.of(projectName));
        this.skipHeader = true;
        return this;
    }
}
