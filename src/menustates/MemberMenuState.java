package src.menustates;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import src.controller.Controller;
import src.model.Member;
import src.model.Project;
import src.model.Task;
import src.view.View;

public class MemberMenuState implements MenuState {
    private final Controller controller;
    private final View view;
    private final Map<String, Supplier<MenuState>> menuActions = new LinkedHashMap<>();

    private record MemberAttributes(
        String projectName, 
        String taskName, 
        String memberName, 
        String newMemberName, 
        String role
    ) {}

    public MemberMenuState(Controller controller, View view, MenuState previousState) {
        this.controller = controller;
        this.view = view;

        menuActions.put("Add Member", () -> addMember());
        menuActions.put("List Members", () -> listMembers());
        menuActions.put("Edit Member", () -> editMember());
        menuActions.put("Delete Member", () -> deleteMember());
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
// Section: Member states
//-------------------------------------------------------------------------

    private MenuState addMember() {
        MemberAttributes attributes = readAttributes(false, true, false, true, true);

        controller.addAssignee(attributes.projectName(), attributes.taskName(), attributes.memberName(), attributes.role());
        view.waitForKeyPress();
        return this;
    }

    private MenuState listMembers() {
        MemberAttributes attributes = readAttributes(true, false, false, false, true);
        String filterString = view.readUserInput("Enter filter string (leave empty for no filter):", null, null, true);

        controller.listMembers(attributes.projectName(), attributes.taskName(), filterString);
        view.waitForKeyPress();
        return this;
    }

    private MenuState editMember() {
        MemberAttributes attributes = readAttributes(true, true, true, true, true);

        controller.editAssignee(attributes.projectName(), attributes.taskName(), attributes.memberName(), attributes.newMemberName(), attributes.role());
        view.waitForKeyPress();
        return this;
    }

    private MenuState deleteMember() {
        MemberAttributes attributes = readAttributes(true, true, true, false, true);

        controller.removeAssignees(attributes.projectName(), attributes.taskName(), Set.of(attributes.memberName()));
        view.waitForKeyPress();
        return this;
    }

//-------------------------------------------------------------------------
// Section: private functions
//-------------------------------------------------------------------------

    private MemberAttributes readAttributes(boolean allowMemberNumber, boolean askForMemberName, boolean askForNewName, boolean askForRole, boolean skipHeader) {
        final boolean shouldClear = !skipHeader;

        controller.listProjects(null);

        String projectName = view.readUserInput(
            "Enter Project name or number:", Project.NAME_PATTERN, "Project name cannot be empty.", shouldClear
        );

        controller.listTasks(projectName, null);

        String taskName = view.readUserInput(
            "Enter Task name or number:", Task.NAME_PATTERN, "Task name cannot be empty.", shouldClear
        );

        if (allowMemberNumber) {
            controller.listMembers(projectName, taskName, null);
        }  

        String memberName = askForMemberName ? view.readUserInput(
            allowMemberNumber ? "Enter Member name or number:" : "Enter Member name:",
            Member.NAME_PATTERN,
            "Member name cannot be empty.",
            shouldClear
        ) : null;

        String newMemberName = askForNewName ? view.readUserInput(
            "Enter new member name:", Member.NAME_PATTERN, "Member name must start with a letter and cannot contain a pipe character or must be empty to keep the current name.", shouldClear
        ) : null;

        String role = askForRole ? view.readUserInput(
            "Enter member role:", null,  null,  true
        ) : null;

        return new MemberAttributes(
            projectName,
            taskName,
            memberName,
            (newMemberName != null && !newMemberName.isBlank()) ? newMemberName : null,
            (role != null && !role.isBlank()) ? role : null
        );
    }
}
