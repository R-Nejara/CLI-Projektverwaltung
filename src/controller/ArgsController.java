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
        //ToDo import projects
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

        this.projects.add(new Project("Test", "", null));

        List<Project> results = this.projects.stream()
                                        .filter(p -> p.getTitle().toLowerCase().contains(searchText))
                                        .toList();

        //ToDo
        //Debug
        results.forEach(p -> System.out.println("- " + p.getTitle()));
    }

    @Override
    public void showProject(String name) {
        //ToDo
    }

    @Override
    public void editProject(String name, String description, LocalDateTime dueDate) {
        //ToDo
    }

    @Override
    public void deleteProjects(String[] projectNames) {
        //ToDo
    }
}