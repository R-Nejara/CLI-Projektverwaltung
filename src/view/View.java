package src.view;

import java.util.List;
import java.util.regex.Pattern;
import src.model.Member;
import src.model.Project;
import src.model.Task;

public interface View {
    public String readUserInput(String message, Pattern pattern, String errorMessage, boolean printHeader);
    public int readUserInput(String[] options, String errorMessage, boolean printHeader);
    public void waitForKeyPress();
    

    public void printMessage(String message);
    public void printWarning(String message);
    public void printError(String message);
    public void printProjectList(List<Project> projects);
    public void printTaskList(List<Task> tasks);
    public void printMemberList(List<Member> members);
    public void printProject(Project project);
    public void printTask(Task task);
}