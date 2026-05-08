package src.commands;

public class ExampleCommand extends BaseCommand {
    public ExampleCommand() {
        super("example", "-e", 0);
    }

    @Override
    public void execute(String[] args) {
        System.out.println(super.getKey());
    }
}
