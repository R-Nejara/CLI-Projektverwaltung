package src.commands;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import src.controller.Controller;

public abstract class BaseCommand implements Command {
    public static final String SHORTCUT_SYMBOL = "-";
    public static final String FLAG_SYMBOL = "--";

    private final Map<String, Command> subCommands = new LinkedHashMap<>();
    protected final Controller controller;
    private final String key;
    private final String shortcut;
    private final int commandLevel;

    /**
     * Initializes a new instance of the BaseCommand class with the specified parameters.
     * @param controller the controller to be used by the command
     * @param key the key that identifies the command
     * @param shortcut the shortcut that identifies the command
     * @param commandLevel the level of the command in the command hierarchy
     */
    protected BaseCommand(Controller controller, String key, String shortcut, int commandLevel) {
        this.controller = controller;
        this.key = key.toLowerCase();
        this.shortcut = BaseCommand.SHORTCUT_SYMBOL + shortcut.toLowerCase();
        this.commandLevel = commandLevel;
    }

    /**
     * Executes the command with the specified arguments.
     * @param args the arguments for the command
     */
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

//-------------------------------------------------------------------------
// Section: Getter
//-------------------------------------------------------------------------

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getShortcut() {
        return this.shortcut;
    }

//-------------------------------------------------------------------------
// Section: Java methods
//-------------------------------------------------------------------------

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String indent = "  ".repeat(this.commandLevel);

        if (!isRoot()) {
            result.append(indent)
                .append(getKey().toUpperCase())
                .append(": ")
                .append(getKey())
                .append("|")
                .append(getShortcut())
                .append("\n");
        }

        subCommands.forEach((name, cmd) -> {
            if (!isShortcut(name)) {
                String subContent = cmd.toString();
                if (!subContent.isEmpty()) {
                    result.append(subContent)
                    .append("\n");
                }
            }
        });

        return result.toString();
    }

//-------------------------------------------------------------------------
// Section: Protected functions
//-------------------------------------------------------------------------

    /**
     * Retrieves the argument at the specified index from the given array of arguments.
     * Validates that the argument is not null, not blank, and starts with a letter.
     *
     * @param args the array of arguments
     * @param index the index of the argument to retrieve
     * @return
     */
    protected String getArg(String[] args, int index) {
        if (args == null || index < 0 || index >= args.length) {
            return null;
        }
        String arg = args[index];

        if (arg != null && !arg.isBlank() && Character.isLetter(arg.charAt(0))) {
            return arg;
        }

        return null;
    }

    /**
     * Retrieves the sub-arguments from the given array of arguments, starting from the second element.
     *
     * @param args the array of arguments
     * @return the sub-arguments
     */
    protected String[] getSubArgs(String[] args) {
        return getSubArgs(args, 1);
    }

    /**
     * Retrieves the sub-arguments from the given array of arguments, starting from the specified index.
     *
     * @param args the array of arguments
     * @param preArgsCount the number of pre-arguments to skip
     * @return the sub-arguments
     */
    protected String[] getSubArgs(String[] args, int preArgsCount) {
        if (args.length < 1) { return new String[0]; }
        return Arrays.copyOfRange(
            args, 
            (preArgsCount > 0) ? preArgsCount : 1,
            args.length
        );
    }

    /**
     * Registers a sub-command to the current command, making it accessible via its key and shortcut.
     *
     * @param command the sub-command to register
     */
    protected void registerSubCommand(Command command) {
        subCommands.put(command.getKey(), command);
        subCommands.put(command.getShortcut(), command);
    }

    /**
     * Retrieves a map of flags and their corresponding values from the given array of arguments.
     * Flags are identified by their prefix (e.g., "--") and are followed by their value.
     *
     * @param args the array of arguments
     * @return a map of flags and their corresponding values
     */
    protected Map<String, String> getFlags(String[] args) {
        final Map<String, String> flags = new LinkedHashMap<>();
        if (args == null) {
            return flags;
        }

        for (int i = 0; i < args.length - 1; i++) {
            final String arg = args[i];

            if (!isFlag(arg)) { 
                continue; 
            }

            final String flagKey = arg.substring(BaseCommand.FLAG_SYMBOL.length());
            final String value = args[i + 1];
            
            if (value != null) { 
                flags.put(flagKey, value);
                i++; // Skip the value in the next iteration
            }
        }
        return flags;
    }

//-------------------------------------------------------------------------
// Section: Private functions
//-------------------------------------------------------------------------

    private boolean isRoot() {
        return getKey().toLowerCase().startsWith("root");
    }

    private boolean isShortcut(String name) {
        return name.startsWith(BaseCommand.SHORTCUT_SYMBOL);
    }

    private boolean isFlag(String name) {
        return name.startsWith(BaseCommand.FLAG_SYMBOL);
    }
}
