package src.controller;

import src.commands.Command;
import src.commands.RootCommand;

public class ArgsController extends BaseController {    

    @Override
    public void run(String[] args) {
        Command root = new RootCommand(this);

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