package src.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Task {
    private final UUID id;
    private String title;
    private String description;
    private State state;
    private List<Member> assignees = new ArrayList<>();
    private LocalDateTime dueDate;

    public Task(UUID id, String title, String description, State state, List<Member> assignees, LocalDateTime dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = (state == null) ? State.OPEN : state;
        this.assignees = assignees;
        this.dueDate = dueDate;
    }

    public Task(String title, String description, State state, List<Member> assignees, LocalDateTime dueDate) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.state = (state == null) ? State.OPEN : state;
        this.assignees = assignees;
        this.dueDate = dueDate;
    }

    // Getter
    public UUID getId() { return this.id; }
    public String getTitle() { return this.title; }
    public String getDescription() { return this.description; }
    public State getState() { return this.state; }
    public List<Member> getAssignees() { return this.assignees; }
    public LocalDateTime getDueDate() { return this.dueDate; }

    // Setter
    public void setTitle(String newTitle) {
        if (newTitle != null && title.isBlank()) { return; } //ToDo: Error handling
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
        // ToDo
    }

    public void setDueDate(LocalDateTime newDueDate) {
        this.dueDate = (newDueDate == null) ? this.dueDate : newDueDate;
    }

    // Overrides
    @Override
    public String toString() {
        return ""; //ToDo
    }
}