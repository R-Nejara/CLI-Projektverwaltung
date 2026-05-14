package src.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Task {
    private String title;
    private String description;
    private State state;
    private List<Member> assignees = new ArrayList<>();
    private LocalDateTime dueDate;

    public Task(String title, String description, State state, List<Member> assignees, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.state = (state == null) ? State.OPEN : state;
        this.assignees = assignees;
        this.dueDate = dueDate;
    }

    public Task(String title, String description, String state, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.state = (state == null) ? State.OPEN : parseState(state);
        this.dueDate = dueDate;

    }

//-------------------------------------------------------------------------
// Section: Getter
//-------------------------------------------------------------------------

    public String getTitle() { return this.title; }
    public String getDescription() { return this.description; }
    public State getState() { return this.state; }
    public List<Member> getAssignees() { return this.assignees; }
    public LocalDateTime getDueDate() { return this.dueDate; }

//-------------------------------------------------------------------------
// Section: Setter
//-------------------------------------------------------------------------

    public void setTitle(String newTitle) {
        if (newTitle != null && newTitle.isBlank()) { return; } //TODO: Error handling
        this.title = newTitle;
    }

    public void setDescription(String newDesciption) {
        this.description = newDesciption;
    }

    public void setState(String newState) {
        this.state = (newState == null) ? this.state : parseState(newState);
    }

    public void addAssignees(Member... newMembers) {
        if (newMembers == null) { return ; }
        this.assignees.addAll(List.of(newMembers));
    }

    public void removeAssignees(Member... members) {
        if (members == null) { return; }
        this.assignees.removeAll(List.of(members));
    }

    public void setDueDate(LocalDateTime newDueDate) {
        this.dueDate = (newDueDate == null) ? this.dueDate : newDueDate;
    }

//-------------------------------------------------------------------------
// Section: private functions
//-------------------------------------------------------------------------

    private State parseState(String input) {
        try {
            return State.valueOf(input.toUpperCase());
        } catch (Exception e) {
            return State.OPEN;
        }
    }

//-------------------------------------------------------------------------
// Section: Java methods
//-------------------------------------------------------------------------

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String due = (dueDate != null) ? dueDate.format(formatter) : "-";
        String assigned = assignees.isEmpty() ? "-" : assignees.stream().map(Member::getName).collect(Collectors.joining(", "));

        return "[%s] %s\n  Beschreibung: %s\n  Fällig: %s\n  Zugewiesen: %s".formatted(state, title, description != null ? description : "-", due, assigned);
    }
}