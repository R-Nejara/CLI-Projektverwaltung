package src.view;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import src.model.Member;
import src.model.Project;
import src.model.State;
import src.model.Task;
import src.utils.DateTimeUtil;

public class DefaultView implements View {
    Scanner scanner = new Scanner(System.in);
    /**
     * User Input wird gelesen und zurückgegeben bis das pattern ihn annimmt
     * 
     * @param message Nachricht die an User gesendet wird
     * @param pattern Regulärer Ausdruck für Eingabeformat
     * @param errorMessage Fehlernachricht für RegEx
     * @param printHeader Schalter für das darstellen der Überschrift
     */
    @Override
    public String readUserInput(String message, Pattern pattern, String errorMessage, boolean printHeader) {
        String userInput;
        if (printHeader) {
            ConsoleFormatter.clear();
        }

        while (true){
            System.out.printf("%s\n> ", message);
            userInput = scanner.nextLine();
            if (pattern == null || userInput.matches(pattern.pattern())) {
                return userInput;
            } else {
                System.out.printf("%s\n", errorMessage);
            }
        }
    }

    @Override
    public int readUserInput(String[] options, String errorMessage, boolean printHeader) {
        String userInput;
        int parsedUserInput;
        ConsoleFormatter.clear();

        while (true) {
            System.out.printf("┌%s Wähle eine Option %s┐\n","─".repeat(9) ,"─".repeat(9));

            for (int i = 0; i < options.length; i++) {
               System.out.printf("│ %d. %s%s│\n", i+1, options[i], " ".repeat(31 - options[i].length() + 2));
            }
            System.out.printf("└%s┘\n","─".repeat(37));
            System.out.printf("> ");
            userInput = scanner.nextLine();

            try {
            parsedUserInput = Integer.parseInt(userInput);

            if (parsedUserInput > 0 && parsedUserInput <= options.length) {
                return parsedUserInput;
            }

            } catch (NumberFormatException e) {
                System.out.println(errorMessage == null ?  e.getMessage() : errorMessage);
            }
        }
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printWarning(String message) {
        System.out.println(message);
    }

    @Override
    public void printError(String message) {
        System.out.println(message);
    }

    @Override
    public void printProjectList(List<Project> projects) {
        int counter = 0;
        int whitespace = 20;
        int boxlength = 110;
        
        System.out.printf("\n┌%s Projekte %s┐\n", "─".repeat(19), "─".repeat(81));
        for (Project project : projects) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateTimeUtil.SIMPLE_FORMAT);
            String deadline = (project.getDueDate() != null) ? project.getDueDate().format(formatter) : "-";
        
            counter++;
            System.out.printf(
                    "│ %d. %s%s\tErledigt: %d\tBearbeitungsbeginn/Offen: %d\tDeadline: %s%s│\n", counter,
                    project.getTitle(),
                    " ".repeat(whitespace - project.getTitle().length()),
                    getTaskStateCount(project.getTasks(), true),
                    getTaskStateCount(project.getTasks(), false),
                    deadline,
                    " ".repeat(5)
            );

        }
        System.out.printf("└%s┘\n","─".repeat(boxlength));
    }

    @Override
    public void printTaskList(List<Task> tasks) {
        for (Task task : tasks) {
            System.out.printf("  - %s\n", task.getTitle());
        }
    }

    @Override
    public void printProject(Project project) {
        final int WHITESPACE = 15;
        int spacingWithTitle = (WHITESPACE * 2) + project.getTitle().length() + 2;
        int amountTaskInProgess = getTaskStateCount(project.getTasks(), false);
        int amountTaskCompleted = getTaskStateCount(project.getTasks(), true);
        int amountTasks = amountTaskInProgess + amountTaskCompleted;
        String emptyLine = " ".repeat(spacingWithTitle);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateTimeUtil.SIMPLE_FORMAT);
        String deadline = (project.getDueDate() != null) ? project.getDueDate().format(formatter) : "-";

        // Project Title Format
        System.out.printf("┌%s %s %s┐\n", "─".repeat(WHITESPACE), project.getTitle(), "─".repeat(WHITESPACE) );

        // Project Description Title
        System.out.printf("│ Projektbeschreibung:%s│", " ".repeat(spacingWithTitle - " Projektbeschreibung:".length()));

        // Empty Line
        System.out.printf("\n│%s│\n", emptyLine);

        // Project Description Output
        printWrapped(project.getDescription(), (WHITESPACE * 2) + project.getTitle().length());

        // Empty line
        System.out.printf("\n│%s│", emptyLine);

        // Tasks
        System.out.printf("\n│ Anzahl an Aufgaben: %d%s│", amountTaskCompleted, " ".repeat(spacingWithTitle - String.valueOf(amountTasks).length() - " Anzahl an Aufgaben: ".length()));
        System.out.printf("\n│ Bearbeitete: %d%s│", amountTaskCompleted, " ".repeat(spacingWithTitle - String.valueOf(amountTaskCompleted).length() - " Bearbeitete: ".length()));
        System.out.printf("\n│ In Bearbeitung/Offen: %d%s│", amountTaskInProgess, " ".repeat(spacingWithTitle - String.valueOf(amountTaskInProgess).length() - " In Bearbeitung/Offen: ".length()));

        // Empty line
        System.out.printf("\n│%s│\n", emptyLine);

        // Deadline Output
        System.out.printf("│ Deadline: %s%s│", deadline, " ".repeat(spacingWithTitle - " Deadline: ".length() - String.valueOf(project.getDueDate()).length()));

        // Box Close
        System.out.printf("\n└%s┘", "─".repeat(spacingWithTitle));

    }

    @Override
    public void waitForKeyPress() {
        System.out.println("\nDrücke die Eingabetaste um fortzusetzen...");
        scanner.nextLine();
    }

    @Override
    public void printMemberList(List<Member> members){
        final int WHITESPACE = 15;
        System.out.printf("┌%s Mitglieder %s┐\n", "─".repeat(WHITESPACE * 2), "─".repeat(WHITESPACE * 2));
        for (Member member : members) {
            System.out.printf("│ Name: %s%s Rolle: %s%s │\n", member.getName(), " ".repeat(WHITESPACE - member.getName().length()) , member.getRole(), " ". repeat(WHITESPACE * 3 - member.getRole().length() - 4));
        }
            System.out.printf("└%s┘\n", "─".repeat((WHITESPACE * 4) + 12));
    }

//-------------------------------------------------------------------------
// Section: Private Methoden
//-------------------------------------------------------------------------

     private int getTaskStateCount(List<Task> tasks, boolean completed){
        return tasks.stream().filter(task -> {
           return completed ? task.getState().equals(State.DONE) : task.getState().equals(State.IN_PROGRESS) || task.getState().equals(State.OPEN);
        }).toList().size();
    }

// limit width of description string
    private static void printWrapped(String text, int width) {
        if (text == null) {
            text = "-";
        }

        for (int i = 0; i < text.length(); i += width) {
            if (text.substring(i, Math.min(i + width, text.length())).length() != width) {
                System.out.printf("│ %s%s │", text.substring(i, Math.min(i + width, text.length())) , " ".repeat(width - text.substring(i, Math.min(i + width, text.length())).length()));
            } else {
                System.out.printf("│ %s │\n", text.substring(i, Math.min(i + width, text.length())));
            }

        }
}

}

