package src.menustates;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import src.controller.Controller;
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
        MemberAttributes attributes = readAttributes(false, true, true);

        controller.addAssignee(attributes.projectName(), attributes.taskName(), attributes.memberName(), attributes.role());
        return this;
    }

    private MenuState editMember() {
        MemberAttributes attributes = readAttributes(true, true, true);

        controller.editAssignee(attributes.projectName(), attributes.taskName(), attributes.memberName(), attributes.newMemberName(), attributes.role());
        return this;
    }

    private MenuState deleteMember() {
        MemberAttributes attributes = readAttributes(false, false, true);

        controller.removeAssignees(attributes.projectName(), attributes.taskName(), Set.of(attributes.memberName()));
        return this;
    }

//-------------------------------------------------------------------------
// Section: private functions
//-------------------------------------------------------------------------

    private MemberAttributes readAttributes(boolean askForNewName, boolean askForRole, boolean skipHeader) {
        final Pattern namePattern = Pattern.compile("^([a-zA-Z][^|]*|)$");
        final boolean shouldClear = !skipHeader;

        controller.listProjects(null);

        String projectName = view.readUserInput(
            "Enter Project name:", Pattern.compile(".+"), "Project name cannot be empty.", shouldClear
        );

        controller.listTasks(projectName, null);

        String taskName = view.readUserInput(
            "Enter Task name:", Pattern.compile(".+"), "Task name cannot be empty.", shouldClear
        );

        String memberName = view.readUserInput(
            "Enter Member name:", Pattern.compile(".+"), "Member name cannot be empty.", shouldClear
        );

        String newMemberName = askForNewName ? view.readUserInput(
            "Enter new member name:", namePattern, "Member name must start with a letter and cannot contain a pipe character or must be empty to keep the current name.", shouldClear
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
