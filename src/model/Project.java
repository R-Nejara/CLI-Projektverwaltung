package src.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;
import src.utils.DateTimeUtil;

public class Project {
    public static final Pattern NAME_PATTERN = Pattern.compile("^(?:([a-zA-Z][^|]*)?|[1-9]\\d*)$");

    private final UUID id;
    private String name;
    private String description;
    private final List<Task> tasks = new ArrayList<>();
    private LocalDateTime dueDate;

    public Project(UUID id, String title, String description, LocalDateTime dueDate) {
        this.id = Objects.requireNonNull(id, "ID darf nicht null sein");
        setTitle(title);
        setDescription(description);
        setDueDate(dueDate);
    }

    public Project(String title, String description, LocalDateTime dueDate) {
        this(UUID.randomUUID(), title, description, dueDate);
    }

//-------------------------------------------------------------------------
// Section: Getter
//-------------------------------------------------------------------------

    public UUID getId() { return id; }
    public String getTitle() { return name; }
    public String getDescription() { return description; }
    public List<Task> getTasks() { return List.copyOf(tasks); }
    public LocalDateTime getDueDate() { return dueDate; }

//-------------------------------------------------------------------------
// Section: Setter
//-------------------------------------------------------------------------

    public final void setTitle(String newTitle) {
        if (newTitle == null) {
            throw new IllegalArgumentException("Projekt-Titel darf nicht null sein.");
        }
        if (!NAME_PATTERN.matcher(newTitle).matches()) {
            throw new IllegalArgumentException("Projekt-Titel entspricht nicht den Mustervorgaben.");
        }
        this.name = newTitle;
    }

    public final void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public final void addTasks(Task... newTasks) {
        if (newTasks == null) return;
        this.tasks.addAll(List.of(newTasks));
    }

    public final void removeTasks(Task... tasks) {
        if (tasks == null) return;
        this.tasks.removeAll(List.of(tasks));
    }

    public final void setDueDate(LocalDateTime newDueDate) {
        this.dueDate = newDueDate;
    }

//-------------------------------------------------------------------------
// Section: Java methods
//-------------------------------------------------------------------------

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateTimeUtil.SIMPLE_FORMAT);
        String due = (dueDate != null) ? dueDate.format(formatter) : "-";

        StringBuilder sb = new StringBuilder();
        sb.append("%s\n  Beschreibung: %s\n  Fällig: %s\n  Aufgaben (%d):"
                .formatted(name, description != null ? description : "-", due, tasks.size()));

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