package src.controller;

import java.time.LocalDateTime;

public interface Controller {
    public void run(String[] args);
    public void addProject(String name, String description, LocalDateTime dueDate);
    public void listProjects(String filter);
    public void showProject(String name);
    public void editProject(String name, String desciption, LocalDateTime dueDate);
    public void deleteProjects(String[] projectNames);
}
