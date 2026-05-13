package src.controller;

import java.time.LocalDateTime;
import java.util.Set;

public interface Controller {
    public void run(String[] args);
    public void addProject(String name, String description, LocalDateTime dueDate);
    public void listProjects(String filter);
    public void showProject(String name);
    public void editProject(String name, String newName, String desciption, LocalDateTime dueDate);
    public void removeProjects(Set<String> projectNames);
}
