package src.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import src.utils.DateTimeUtil;

public class Task {
    public static final Pattern NAME_PATTERN = Pattern.compile("^(?:([a-zA-Z][^|]*)?|[1-9]\\d*)$");

    private String title;
    private String description;
    private State state;
    private Priority priority;
    private final List<Member> assignees = new ArrayList<>();
    private LocalDateTime dueDate;

    public Task(String title, String description, State state, Priority priority, List<Member> assignees, LocalDateTime dueDate) {
        setTitle(title);
        setDescription(description);
        this.state = (state != null) ? state : State.OPEN;
        this.priority = (priority != null) ? priority : Priority.MEDIUM;
        setDueDate(dueDate);
        
        if (assignees != null) {
            this.assignees.addAll(assignees);
        }
    }

    public Task(String title, String description, String state, String priority, LocalDateTime dueDate) {
        setTitle(title);
        setDescription(description);
        this.state = parseState(state);
        this.priority = parsePriority(priority);
        setDueDate(dueDate);
    }

//-------------------------------------------------------------------------
// Section: Getter
//-------------------------------------------------------------------------

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public State getState() { return state; }
    public Priority getPriority() { return priority; }
    public List<Member> getAssignees() { return List.copyOf(assignees); }
    public LocalDateTime getDueDate() { return dueDate; }

//-------------------------------------------------------------------------
// Section: Setter
//-------------------------------------------------------------------------

    public final void setTitle(String newTitle) {
        if (newTitle == null) {
            throw new IllegalArgumentException("Titel darf nicht null sein.");
        }
        if (!NAME_PATTERN.matcher(newTitle).matches()) {
            throw new IllegalArgumentException("Titel entspricht nicht den Mustervorgaben.");
        }
        this.title = newTitle;
    }

    public final void setDescription(String newDescription) { 
        this.description = newDescription;
    }

    public final void setState(State newState) {
        this.state = Objects.requireNonNull(newState, "Status darf nicht null sein.");
    }

    public final void setState(String newState) {
        this.state = parseState(newState);
    }

    public final void setPriority(Priority newPriority) {
        this.priority = Objects.requireNonNull(newPriority, "Priorität darf nicht null sein.");
    }

    public final void setPriority(String newPriority) {
        this.priority = parsePriority(newPriority);
    }

    public final void addAssignees(Member... newMembers) {
        if (newMembers == null) return;
        this.assignees.addAll(List.of(newMembers));
    }

    public final void removeAssignees(Member... members) {
        if (members == null) return;
        this.assignees.removeAll(List.of(members));
    }

    public final void setDueDate(LocalDateTime newDueDate) {
        this.dueDate = newDueDate;
    }

//-------------------------------------------------------------------------
// Section: Helper methods
//-------------------------------------------------------------------------

    private State parseState(String input) {
        if (input == null) return State.OPEN;
        try {
            return State.valueOf(input.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            return State.OPEN;
        }
    }

    private Priority parsePriority(String input) {
        if (input == null) return Priority.MEDIUM;
        try {
            return Priority.valueOf(input.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            return Priority.MEDIUM;
        }
    }

//-------------------------------------------------------------------------
// Section: Java methods
//-------------------------------------------------------------------------

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateTimeUtil.SIMPLE_FORMAT);
        String due = (dueDate != null) ? dueDate.format(formatter) : "-";
        
        String assigned = assignees.isEmpty() 
            ? "-" 
            : assignees.stream()
                       .map(m -> "%s (%s)".formatted(m.getName(), m.getRole()))
                       .collect(Collectors.joining(", "));

        return "[%s] %s\n  Beschreibung: %s\n  Priorität: %s\n  Fällig: %s\n  Zugewiesen: %s"
                .formatted(state, title, description != null ? description : "-", priority, due, assigned);
    }
}