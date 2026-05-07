package src.commands;

public class ExampleCommand implements Command {
    private final static String KEY = "example";
    private final static String SHORTCUT = "e";

    public ExampleCommand() {}

    @Override
    public void execute(String[] args) {}

    @Override
    public String getKey() { return KEY; }

    @Override
    public String getShortcut() { return SHORTCUT; }
}
