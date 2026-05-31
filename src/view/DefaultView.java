package src.view;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import src.model.Project;
import src.model.State;
import src.model.Task;

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
            System.out.printf("Wähle eine Option:\n");

            for (int i = 0; i < options.length; i++) {
               System.out.printf("%d. %s\n", i+1, options[i]);
            }
            System.out.printf("> ");
            userInput = scanner.nextLine();

            try {
            parsedUserInput = Integer.parseInt(userInput);

            if (parsedUserInput > 0 && parsedUserInput <= options.length) {
                return parsedUserInput;
            }
            throw new IndexOutOfBoundsException();

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
        int boxlength = 76;
        
        System.out.printf("\n%s Projekte %s\n\n", "─".repeat(19), "─".repeat(47));
        for (Project project : projects) {
            counter++;
            System.out.printf(
                    "%d. %s%s\tErledigt: %d\tBearbeitungsbeginn/Offen: %d\n\n", counter,
                    project.getTitle(),
                    " ".repeat(whitespace - project.getTitle().length()),
                    getTaskStateCount(project.getTasks(), true),
                    getTaskStateCount(project.getTasks(), false)
            );

        }
        System.out.println("─".repeat(boxlength));
    }

    @Override
    public void printTaskList(List<Task> tasks) {
        for (Task task : tasks) {
            System.out.printf("  - %s\n", task.getTitle());
        }
    }

    @Override
    public void printProject(Project project) {}
     private int getTaskStateCount(List<Task> tasks, boolean completed){
        return tasks.stream().filter(task -> {
           return completed ? task.getState().equals(State.DONE) : task.getState().equals(State.IN_PROGRESS) || task.getState().equals(State.OPEN);
        }).toList().size();
    }
}
