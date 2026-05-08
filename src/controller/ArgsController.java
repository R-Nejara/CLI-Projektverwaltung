package src.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import src.commands.Command;
import src.commands.ExampleCommand;

public class ArgsController {
    private final Map<String, Consumer<String[]>> commands = new HashMap<>();

    public ArgsController() {
        registerCommand(new ExampleCommand());
    }

    public void registerCommand(Command command) {
        commands.put(command.getKey(), (args) -> command.execute(args));
        commands.put(command.getShortcut(), (args) -> command.execute(args));
    }

    public void handleInput(String[] args) {
        String cmd = args[0].toLowerCase();

        if (commands.containsKey(cmd)) {
            commands.get(cmd).accept(args);
        } else {
            System.out.println("Error: Unknown command.");
        }
    }
}