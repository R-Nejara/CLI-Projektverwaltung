package src.controller;

import src.commands.Command;
import src.commands.RootCommand;

public class ArgsController {
    public void handleInput(String[] args) {
        Command root = new RootCommand();

        if (args.length > 0 
            && (args[0].equals("help")
            || args[0].equals("-h"))
        ) {
            System.out.println(root.toString());
            return;
        }
        root.execute(args);
    }
}