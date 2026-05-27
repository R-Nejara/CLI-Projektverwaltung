package src.view;

import java.util.List;
import java.util.regex.Pattern;
import src.model.Project;
import src.model.Task;

public class DefaultView implements View {
    @Override
    public String readUserInput(String message, Pattern pattern, String errorMessage, Boolean printHeader) {
        return null;
    }

    @Override
    public Integer readUserInput(String[] options, String errorMessage, Boolean printHeader) {
        return null;
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
        for (Project project : projects) {
            System.out.println((project.getTitle()));
        }
    }

    @Override
    public void printTaskList(List<Task> tasks) {
        for (Task task : tasks) {
            System.out.printf("  - %s\n", task.getTitle());
        }
    }

    @Override
    public void printProject(Project project) {}
}
