package src.model;

import java.util.UUID;
import java.util.regex.Pattern;

public class Member {
    public static final Pattern NAME_PATTERN = Pattern.compile("^(?:([a-zA-Z][^|]*)?|[1-9]\\d*)$");
    
    private final UUID id;
    private String name;
    private String role;

    public Member(UUID id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Member(String name, String role) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.role = role;
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

    public void setName(String newName) {
        if (newName != null && newName.isBlank()) { return; } //TODO: Error handling
        this.name = newName;
    }

    public void setRole(String newRole) {
        if (newRole != null && newRole.isBlank()) { return; } //TODO: Error handling
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
