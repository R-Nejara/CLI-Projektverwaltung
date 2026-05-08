package src.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Project {
    private final UUID id;
    private String name;
    private String description;
    private final List<Task> tasks;
    private LocalDateTime dueDate;

    public Project(UUID id, String name, String desciption, LocalDateTime dueDate) {
        this.id = id;
        this.name = name;
        this.description = desciption;
        this.tasks = new ArrayList<>();
        this.dueDate = dueDate;
    }

    public Project(String name, String desciption, LocalDateTime dueDate) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = desciption;
        this.tasks = new ArrayList<>();
        this.dueDate = dueDate;
    }

    // Getter
    public UUID getId() { return this.id; }
    public String getTitle() { return this.name; }
    public String getDescription() { return this.description; }
    public List<Task> getTasks() { return this.tasks; }
    public LocalDateTime getDueDate() { return this.dueDate; }

    // Setter
    public void setName(String newName) {
        if (newName != null && newName.isBlank()) { return; } //ToDO: Error handling
        this.name = newName;
    }

    public void setDescription(String newDesciption) {
        this.description = newDesciption;
    }

    public void addTask(Task newTask) {
        //ToDo
    }

    public void removeTask(Task task) {
        //ToDo
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
