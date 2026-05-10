package src.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    // Getter
    public String getTitle() { return this.title; }
    public String getDescription() { return this.description; }
    public State getState() { return this.state; }
    public List<Member> getAssignees() { return this.assignees; }
    public LocalDateTime getDueDate() { return this.dueDate; }

    // Setter
    public void setTitle(String newTitle) {
        if (newTitle != null && title.isBlank()) { return; } //TODO: Error handling
        this.title = newTitle;
    }

    public void setDescription(String newDesciption) {
        this.description = newDesciption;
    }

    public void setState(State newState) {
        this.state = (newState == null) ? this.state : state;
    }

    public void addAssignees(Member... newMembers) {
        if (newMembers == null) { return ; }
        this.assignees.addAll(List.of(newMembers));
    }

    public void removeAssignees(Member... members) {
        // TODO
    }

    public void setDueDate(LocalDateTime newDueDate) {
        this.dueDate = (newDueDate == null) ? this.dueDate : newDueDate;
    }

    // Overrides
    @Override
    public String toString() {
        return ""; //TODO
    }
}