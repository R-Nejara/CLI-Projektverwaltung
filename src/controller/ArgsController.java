package src.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import src.commands.Command;
import src.commands.RootCommand;
import src.model.Project;

public class ArgsController implements Controller {
    private final List<Project> projects;

    public ArgsController() {
        //TODO import projects
        this.projects = new ArrayList<>();
    }
    
    @Override
    public void run(String[] args) {
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

    @Override
    public void addProject(String name, String description, LocalDateTime dueDate) {
        if (name == null || name.isBlank()) {
            //TODO
            return;
        }

        if (getProjectByName(name) != null) {
            //TODO
            return;
        }

        Project newProject = new Project(name, description, dueDate);
        projects.add(newProject);

        //Debug
        System.out.printf("Project added [Name: %s, Desciption: %s, DueDate: %s]\n", name, description, dueDate);
    }

    @Override
    public void listProjects(String filter) {
        String searchText = (filter != null) ? filter.toLowerCase() : "";

        List<Project> results = this.projects.stream()
                                        .filter(p -> p.getTitle().toLowerCase().contains(searchText))
                                        .toList();

        //TODO
        //Debug
        results.forEach(p -> System.out.println("- " + p.getTitle()));
    }

    @Override
    public void showProject(String name) {
        Project result = getProjectByName(name);

        System.out.println("- " + ((result != null) ? result.getTitle() : "null"));
    }

    @Override
    public void editProject(String name, String newName, String description, LocalDateTime dueDate) {
        Project project = getProjectByName(name);
        if (project == null) {
            //TODO
            return;
        } 

        if (newName != null && !newName.isBlank()) {
            project.setName(newName);
        }

        if (description != null && !description.isBlank()) {
            project.setDescription(description);
        }

        if (dueDate != null) {
            project.setDueDate(dueDate);
        }
    }

    @Override
    public void removeProjects(String[] projectNames) {
        if (projectNames == null) {
            //TODO
            return;
        }
        //TODO
    }

    // private functions

    private Project getProjectByName(String name) {
        String searchText = (name != null) ? name.toLowerCase() : null;

        if (searchText == null) {
            return null;
        }

        return this.projects
                    .stream()
                    .filter(p -> p.getTitle().equalsIgnoreCase(searchText))
                    .findFirst()
                    .orElse(null);
    }
}