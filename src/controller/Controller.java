package src.controller;

import java.time.LocalDateTime;
import java.util.Set;

public interface Controller {
    public void run(String[] args);

//-------------------------------------------------------------------------
// Section: Project
//-------------------------------------------------------------------------

    public void addProject(String name, String description, LocalDateTime dueDate);
    public void listProjects(String filter);
    public void showProject(String name);
    public void editProject(String name, String newName, String desciption, LocalDateTime dueDate);
    public void removeProjects(Set<String> projectNames);

//-------------------------------------------------------------------------
// Section: Task
//-------------------------------------------------------------------------

    public void addTask(String projectName, String name, String description, String state, String priority, LocalDateTime dueDate);
    public void listTasks(String projectName,String filter);
    public void editTask(String projectName, String taskName, String newName, String description, String state, String priority, LocalDateTime dueDate);
    public void removeTasks(String projectName, Set<String> taskNames);

//-------------------------------------------------------------------------
// Section: Assignees/Member
//-------------------------------------------------------------------------

    public void addAssignee(String projectName, String taskName, String name, String role);
    public void editAssignee(String projectName, String taskName, String memberName, String name, String role);
    public void removeAssignees(String projectName, String taskName, Set<String> assigneeNames);
}
