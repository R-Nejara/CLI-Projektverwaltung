package src.commands;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import src.controller.Controller;

public abstract class BaseCommand implements Command {
    private final Map<String, Command> subCommands = new LinkedHashMap<>();
    protected final Controller controller;
    private final String KEY;
    private final String SHORTCUT;
    private final int commandLevel;

    protected BaseCommand(Controller controller, String key, String shortcut, int commandLevel) {
        this.controller = controller;
        this.KEY = key.toLowerCase();
        this.SHORTCUT = shortcut.toLowerCase();
        this.commandLevel = commandLevel;
    }

    @Override
    public void execute(String[] args) {
        if (subCommands.isEmpty()) { return; }

        if (args.length <= 0) { 
            System.err.println("Error: Missing sub-command.");
            return; 
        }
        String arg = args[0].toLowerCase();
        Command command = subCommands.get(arg);

        if (command != null) {
            command.execute(getSubArgs(args));
        } else {
            System.err.println("Error: Unknown command.");
        }
    }

    @Override
    public String getKey() {
        return this.KEY;
    }

    @Override
    public String getShortcut() {
        return this.SHORTCUT;
    }

    @Override
    public String toString() {
        if (subCommands.isEmpty()) {
            return getKey().toUpperCase() + ": (" + getKey() + "|" + getShortcut() + ")";
        }

        StringBuilder result = new StringBuilder();
        if (!isRoot()) {
            result
                .append("\t".repeat(this.commandLevel))
                .append(KEY.toUpperCase()).append(":\n");
        }

        subCommands.forEach((name, cmd) -> {
            if (!isShortcut(name)) {
                result
                    .append("\t".repeat(this.commandLevel))
                    .append(cmd.toString()).append("\n");
            }
        });

        return result.toString();
    }

    protected String[] getSubArgs(String[] args) {
        if (args.length < 1) { return new String[0]; }
        return Arrays.copyOfRange(args, 1, args.length);
    }

    protected void registerSubCommand(Command command) {
        subCommands.put(command.getKey(), command);
        subCommands.put(command.getShortcut(), command);
    }

    private boolean isRoot() {
        return getKey().toLowerCase().startsWith("root");
    }

    private boolean isShortcut(String name) {
        return name.startsWith("-");
    }

}
