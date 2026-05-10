package src.controller;

import java.time.LocalDateTime;
import java.util.List;
import src.commands.Command;
import src.commands.RootCommand;
import src.model.Project;

public class ArgsController {
    private List<Project> projects;

    public ArgsController() {
        //ToDo import projects
    }
    
    public void handleInput(String[] args) {
        Command root = new RootCommand(this);

        if (args.length > 0 
            && (args[0].equals("help")
            || args[0].equals("-h"))
        ) {
            System.out.println(root.toString());
            return;
        }
        root.execute(args);
    }

    public void addProject(String name, String description, LocalDateTime dueDate) {
        if (name == null || name.isBlank()) {
            //ToDo
            return;
        }

        Project newProject = new Project(name, description, dueDate);
        projects.add(newProject);

        System.out.printf("Project added [Name: %s, Desciption: %s, DueDate: %s]\n", name, description, dueDate);
    }

    public void listProjects(String filter) {
        //ToDo
    }

    public void showProject(String id) {
        //ToDo
    }

    public void editProject(String id, String name, String description, LocalDateTime dueDate) {
        //ToDo
    }

    public void deleteProjects(String[] projectIds) {
        //ToDo
    }
}