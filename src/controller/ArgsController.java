package src.controller;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import src.commands.Command;
import src.commands.RootCommand;
import src.model.Project;

public class ArgsController implements Controller {
    private final Set<Project> projects;

    public ArgsController() {
        //ToDo import projects
        this.projects = new HashSet<>();
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
            //ToDo
            return;
        }

        if (!projects.stream().noneMatch(project -> project.getTitle().equalsIgnoreCase(name))) {
            //ToDo
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

        //ToDo
        //Debug
        results.forEach(p -> System.out.println("- " + p.getTitle()));
    }

    @Override
    public void showProject(String name) {
        String searchText = (name != null) ? name.toLowerCase() : "";

        Project result = this.projects.stream()
                                        .filter(p -> p.getTitle().toLowerCase().contains(searchText))
                                        .findFirst()
                                        .orElse(null);

        System.out.println("- " + ((result != null) ? result.getTitle() : "null"));
    }

    @Override
    public void editProject(String name, String description, LocalDateTime dueDate) {
        //ToDo
    }

    @Override
    public void removeProjects(String[] projectNames) {
        if (projectNames == null) {
            //ToDo
            return;
        }
        //ToDo
    }
}