package src.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import src.controller.Controller;

public abstract class BaseCommand implements Command {
    public static final String SHORTCUT_SYMBOL = "-";
    public static final String FLAG_SYMBOL = "--";

    private final Map<String, Command> subCommands = new LinkedHashMap<>();
    protected final Controller controller;
    private final String KEY;
    private final String SHORTCUT;
    private final int commandLevel;

    protected BaseCommand(Controller controller, String key, String shortcut, int commandLevel) {
        this.controller = controller;
        this.KEY = key.toLowerCase();
        this.SHORTCUT = BaseCommand.SHORTCUT_SYMBOL + shortcut.toLowerCase();
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

//-------------------------------------------------------------------------
// Section: Getter
//-------------------------------------------------------------------------

    @Override
    public String getKey() {
        return this.KEY;
    }

    @Override
    public String getShortcut() {
        return this.SHORTCUT;
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

    protected String[] getSubArgs(String[] args) {
        return getSubArgs(args, 1);
    }

    protected String[] getSubArgs(String[] args, int preArgsCount) {
        if (args.length < 1) { return new String[0]; }
        return Arrays.copyOfRange(
            args, 
            (preArgsCount > 0) ? preArgsCount : 1,
            args.length
        );
    }

    protected void registerSubCommand(Command command) {
        subCommands.put(command.getKey(), command);
        subCommands.put(command.getShortcut(), command);
    }

    protected Map<String, String> getFlags(String[] args) {
        Map<String, String> flags = new LinkedHashMap<>();

        for (int i=0; i < args.length-1; i++) {
            String arg = args[i];

            if (!isFlag(arg)) { continue; }

            String flag = !arg.isBlank() ? arg : null;
            String value = !args[i+1].isBlank() ? args[i+1] : null;
            
            if (flag != null && value != null) { 
                flag = flag.replaceAll(BaseCommand.FLAG_SYMBOL, "");
                flags.put(flag, value);
                i++;
            }
        }
        return flags;
    }

    protected LocalDateTime parseDateTime(String input) {
        if (input == null || input.isBlank()) { return null; }

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("dd.MM.yyyy")
            .optionalStart()
            .appendPattern(" HH:mm")
            .optionalEnd()
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 23)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 59)
            .toFormatter();

        try {
            return LocalDateTime.parse(input, formatter);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
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
