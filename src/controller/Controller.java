package src.controller;

import java.time.LocalDateTime;
import java.util.Set;

public interface Controller {
    public void run(String[] args);
    //Project
    public void addProject(String name, String description, LocalDateTime dueDate);
    public void listProjects(String filter);
    public void showProject(String name);
    public void editProject(String name, String newName, String desciption, LocalDateTime dueDate);
    public void removeProjects(Set<String> projectNames);

    //Task
    public void addTask(String projectName, String name, String description, String state);
    public void listTasks(String projectName, String filter);
    public void showTask(String projectName, String name);
    public void editTask(String projectName, String newName, String description, String state);
    public void removeTasks(String projectName, Set<String> taskNames);
}
