package src.model;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public class Member {
    public static final Pattern NAME_PATTERN = Pattern.compile("^(?:([a-zA-Z][^|]*)?|[1-9]\\d*)$");

    private final UUID id;
    private String name;
    private String role;

    public Member(UUID id, String name, String role) {
        this.id = Objects.requireNonNull(id, "ID darf nicht null sein");
        
        setName(name);
        setRole(role);
    }

    public Member(String name, String role) {
        this(UUID.randomUUID(), name, role);
    }

//-------------------------------------------------------------------------
// Section: Getter
//-------------------------------------------------------------------------

    public UUID getId() { return this.id; }
    public String getName() { return this.name; }
    public String getRole() { return this.role; }

//-------------------------------------------------------------------------
// Section: Setter
//-------------------------------------------------------------------------

    public final void setName(String newName) {
        if (newName == null) {
            throw new IllegalArgumentException("Name darf nicht null sein.");
        }
        if (!NAME_PATTERN.matcher(newName).matches()) {
            throw new IllegalArgumentException("Name entspricht nicht den Vorgaben (muss mit Buchstabe beginnen und darf keine Pipes enthalten).");
        }
        this.name = newName;
    }

    public final void setRole(String newRole) {
        if (newRole != null && newRole.isBlank()) {
            throw new IllegalArgumentException("Rolle darf nicht nur aus Leerzeichen bestehen.");
        }
        this.role = newRole;
    }

//-------------------------------------------------------------------------
// Section: Java methods
//-------------------------------------------------------------------------

    @Override
    public String toString() {
        return "%s (%s)".formatted(name, role != null ? role : "-");
    }
}
