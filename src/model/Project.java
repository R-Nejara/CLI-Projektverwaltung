package src.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import src.utils.DateTimeUtil;

public class Project {
    public static final Pattern NAME_PATTERN = Pattern.compile("^(?:([a-zA-Z][^|]*)?|[1-9]\\d*)$");

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

//-------------------------------------------------------------------------
// Section: Getter
//-------------------------------------------------------------------------

    public UUID getId() { return this.id; }
    public String getTitle() { return this.name; }
    public String getDescription() { return this.description; }
    public List<Task> getTasks() { return this.tasks; }
    public LocalDateTime getDueDate() { return this.dueDate; }

//-------------------------------------------------------------------------
// Section: Setter
//-------------------------------------------------------------------------

    public void setName(String newName) {
        if (newName != null && newName.isBlank()) { return; } //TODO: Error handling
        this.name = newName;
    }

    public void setDescription(String newDesciption) {
        this.description = newDesciption;
    }

    public void addTasks(Task... newTasks) {
        if (newTasks == null) { return; } 
        this.tasks.addAll(List.of(newTasks));
    }

    public void removeTasks(Task... tasks) {
        if (tasks == null) { return; } 
        this.tasks.removeAll(List.of(tasks));
    }

    public void setDueDate(LocalDateTime newDueDate) {
        this.dueDate = (newDueDate == null) ? this.dueDate : newDueDate;
    }

//-------------------------------------------------------------------------
// Section: Java methods
//-------------------------------------------------------------------------

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateTimeUtil.SIMPLE_FORMAT);
        String due = (dueDate != null) ? dueDate.format(formatter) : "-";

        StringBuilder sb = new StringBuilder();
        sb.append("%s\n  Beschreibung: %s\n  Fällig: %s\n  Aufgaben (%d):".formatted(name, description != null ? description : "-", due, tasks.size()));

        if (tasks.isEmpty()) {
            sb.append("\n    Keine Aufgaben.");
        } else {
            for (Task task : tasks) {
                sb.append("\n    ").append(task.toString().replace("\n", "\n    "));
            }
        }
        return sb.toString();
    }
}
