package src.commands;

public abstract class BaseCommand implements Command {
    private final String KEY;
    private final String SHORTCUT;

    protected BaseCommand(String key, String shortcut) {
        this.KEY = key.toLowerCase();
        this.SHORTCUT = shortcut.toLowerCase();
    }

    @Override
    public String getKey() {
        return this.KEY;
    }

    @Override
    public String getShortcut() {
        return this.SHORTCUT;
    }
}
