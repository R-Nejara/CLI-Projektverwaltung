package src.view;

import java.util.List;
import java.util.regex.Pattern;
import src.model.Project;
import src.model.Task;

public interface View {
    public String readUserInput(String message, Pattern pattern, String errorMessage, Boolean printHeader);
    public Integer readUserInput(String[] options, String errorMessage, Boolean printHeader);

    public void printMessage(String message);
    public void printWarning(String message);
    public void printError(String message);
    public void printProjectList(List<Project> projects);
    public void printTaskList(List<Task> tasks);
    public void printProject(Project project);
}