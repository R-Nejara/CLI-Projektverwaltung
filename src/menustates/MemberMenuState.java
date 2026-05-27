package src.menustates;

import java.util.Set;
import java.util.regex.Pattern;
import src.controller.Controller;
import src.view.View;

public class MemberMenuState implements MenuState {
    private final Controller controller;
    private final View view;
    private final MenuState previousState;

    private Boolean skipHeader = false;

    public MemberMenuState(Controller controller, View view, MenuState previousState) {
        this.controller = controller;
        this.view = view;
        this.previousState = previousState;
    }

//-------------------------------------------------------------------------
// Section: Handle
//-------------------------------------------------------------------------
    
    @Override
    public MenuState handle() {
        String[] options = {"Add Member", "Edit Member", "Delete Member", "Back to Main Menu"}; 

        Integer userSelection = view.readUserInput(
            options, 
            "Invalid option. Please select a valid option from the menu.", 
            !skipHeader
        );

        switch (userSelection) {
            case 1 -> { return addMember(); }
            case 2 -> { return editMember(); }
            case 3 -> { return deleteMember(); }
            case 4 -> { return previousState; }
            default -> {
                view.printError("Invalid option. Please select a valid option from the menu.");
                return this;
            }
        }
    }

//-------------------------------------------------------------------------
// Section: Member states
//-------------------------------------------------------------------------

    private MenuState addMember() {
        String[] attributes = readAttributes(false, true);

        controller.addAssignee(attributes[0], attributes[1], attributes[2], attributes[4]);
        this.skipHeader = true;
        return this;
    }

    private MenuState editMember() {
        String[] attributes = readAttributes(true, true);

        controller.editAssignee(attributes[0], attributes[1], attributes[2], attributes[3], attributes[4]);
        this.skipHeader = true;
        return this;
    }

    private MenuState deleteMember() {
        String[] attributes = readAttributes(false, false);

        controller.removeAssignees(attributes[0], attributes[1], Set.of(attributes[2]));
        this.skipHeader = true;
        return this;
    }

//-------------------------------------------------------------------------
// Section: private functions
//-------------------------------------------------------------------------

    private String[] readAttributes(Boolean askForNewName, Boolean askForRole) {
        String projectName = view.readUserInput(
            "Enter Project name:", 
            Pattern.compile(".+"),
            "Project name cannot be empty.",
            true
        );

        String taskName = view.readUserInput(
            "Enter Task name:", 
            Pattern.compile(".+"),
            "Task name cannot be empty.",
            true
        );

        String memberName = view.readUserInput(
            "Enter Member name:", 
            Pattern.compile(".+"),
            "Member name cannot be empty.",
            true
        );

        String newMemberName = askForNewName ? view.readUserInput(
            "Enter new member name:", 
            Pattern.compile(".+"),
            "Member name cannot be empty.",
            true
        ) : null;

        String role = askForRole ? view.readUserInput(
            "Enter member role:", 
            null, 
            null, 
            true
        ) : null;

        return new String[]{projectName, taskName, memberName, newMemberName, role};
    }
}
