package src.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Task {
    private final UUID id;
    private String title;
    private String description;
    private State state;
    private LocalDateTime dueDate;

    public Task(UUID id, String title, String description, State state, LocalDateTime dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = (state == null) ? State.OPEN : state;
        this.dueDate = dueDate;
    }

    public Task(String title, String description, State state, LocalDateTime dueDate) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.state = (state == null) ? State.OPEN : state;
        this.dueDate = dueDate;
    }

    // Getter
    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public State getState() { return state; }
    public LocalDateTime getDueDate() { return dueDate; }

    // Setter
    public void setTitle(String newTitle) {
        if (newTitle != null && title.isBlank()) { return; } //ToDO: Error handling
        this.title = newTitle;
    }

    public void setDescription(String newDesciption) {
        this.description = newDesciption;
    }

    public void setState(State newState) {
        this.state = (newState == null) ? this.state : state;
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