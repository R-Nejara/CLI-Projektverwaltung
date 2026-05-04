package src.commands;

public interface Command {
    void execute(String[] args);
    String getKey();
    String getShortcut();
}
