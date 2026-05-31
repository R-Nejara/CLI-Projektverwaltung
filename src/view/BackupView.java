package src.view;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import src.model.Project;
import src.model.Task;

public class BackupView implements View {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readUserInput(String message, Pattern pattern, String errorMessage, boolean printHeader) {
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
    public int readUserInput(String[] options, String errorMessage, boolean printHeader) {
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
    public void waitForKeyPress() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
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
    public void printTaskList(List<Task> tasks) {
        System.out.printf("\nTasks:\n");
        for (Task task : tasks) {
            System.out.printf("  - %s\n", task.getTitle());
        }
    }

    @Override
    public void printProject(Project project) {
        System.out.println("Project: " + project.getTitle());
        System.out.println("Description: " + project.getDescription());
        System.out.println("Deadline: " + project.getDueDate());

        System.out.println("Tasks:");
        project.getTasks().forEach(task -> {
            System.out.println("  - " + task.getTitle() + " (Due: " + task.getDueDate() + ", State: " + task.getState() + ")");
            System.out.println("    Members: ");
            task.getAssignees().forEach(assignee -> {
                System.out.println("      - " + assignee.getName() + " (Role: " + assignee.getRole() + ")");
            });
        });
    }
}
