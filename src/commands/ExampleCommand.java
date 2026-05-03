package src.commands;

public class ExampleCommand implements Command {
    private final static String KEY = "example";

     public ExampleCommand() {}

     @Override
    public void execute(String[] args) {}

    @Override
    public String getKey() { return KEY; }
}
