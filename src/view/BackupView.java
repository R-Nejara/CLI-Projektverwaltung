package src.view;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import src.model.Project;

public class BackupView implements View {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readUserInput(String message, Pattern pattern, String errorMessage, Boolean printHeader) {
        String userInput;

        if (printHeader) {
            ConsoleFormatter.clear();
        }

        while (true) {
            System.out.printf("\n%s\n> ", message);
            userInput = scanner.nextLine();

            if (pattern == null || userInput.matches(pattern.pattern())) {
                return userInput;
            } else {
                ConsoleFormatter.clear();
                System.out.println(ConsoleFormatter.formatError(errorMessage));
            }
        }
    }

    @Override
    public Integer readUserInput(String[] options, String errorMessage, Boolean printHeader) {
        String userInput;

        if (printHeader) {
            ConsoleFormatter.clear();
        }

        while (true) {
            System.out.printf("\nPlease select an option:\n");
            for (int i = 0; i < options.length; i++) {
                System.out.printf("  %d. %s\n", i + 1, options[i]);
            }
            System.out.print("> ");
            userInput = scanner.nextLine();

            try {
                int selection = Integer.parseInt(userInput);
                if (selection >= 1 && selection <= options.length) {
                    return selection;
                } else {
                    ConsoleFormatter.clear();
                    System.out.println(ConsoleFormatter.formatError(errorMessage));
                }
            } catch (NumberFormatException e) {
                ConsoleFormatter.clear();
                System.out.println(ConsoleFormatter.formatError(errorMessage));
            }
        }
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printWarning(String message) {
        System.out.println(ConsoleFormatter.formatWarning(message));
    }

    @Override
    public void printError(String message) {
        System.out.println(ConsoleFormatter.formatError(message));
    }

    @Override
    public void printProjectList(List<Project> projects) {
        System.out.printf("\nProjects:\n");
        for (Project project : projects) {
            System.out.printf("  - %s\n", project.getTitle());
        }
    }

    @Override
    public void printProject(Project project) {
        System.out.println("Project: " + project.getTitle());
        System.out.println("Description: " + project.getDescription());
        System.out.println("Deadline: " + project.getDueDate());
    }
}
