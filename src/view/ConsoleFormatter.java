package src.view;

public final class ConsoleFormatter {
    public static final String RESET = "\u001B[0m";

    public static final String BOLD = "\u001B[1m";

    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";

    public static String formatError(String errorMsg) {
        return BOLD + RED + "Error: " + RESET + errorMsg;
    }

    public static String formatWarning(String warningMsg) {
        return BOLD + YELLOW + "Warning: " + RESET + warningMsg;
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.printf("========================== Projektverwaltung CLI ==========================\n\n");
    }
}
