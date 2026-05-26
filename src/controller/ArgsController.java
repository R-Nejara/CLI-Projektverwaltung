package src.controller;

import src.commands.Command;
import src.commands.RootCommand;
import src.view.View;

public class ArgsController extends BaseController {  
    private final static String HELP_KEY = "help"; 
    private final static String HELP_SHORTCUT = "-h"; 

    public ArgsController(View view) {
        super(view);
    }

    @Override
    public void run(String[] args) {
        Command root = new RootCommand(this);
        String rootArg = args[0].toLowerCase();

        if (args.length > 0 && (rootArg.equals(HELP_KEY) || rootArg.equals(HELP_SHORTCUT))) {
            System.out.println(root.toString());
            return;
        }
        root.execute(args);
    }
}