package src.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import src.model.Project;
import src.view.DefaultView;
import src.view.View;

public abstract class BaseController implements Controller {
    private final View view = new DefaultView();
    private final List<Project> projects;

    protected  BaseController() {
        //TODO import projects
        this.projects = new ArrayList<>();

    }

    @Override
    public void addProject(String name, String description, LocalDateTime dueDate) {
        Project similaProject = getProjectByName(name);
        if (similaProject != null) {
            view.printError(String.format("A project with the name '%s' already exists.", name));
            return;
        } else if ((similaProject == null && name == null)) {
            return;
        }

        Project newProject = new Project(name, description, dueDate);
        projects.add(newProject);

        view.printMessage(String.format("Project added [Name: %s, Desciption: %s, DueDate: %s]\n", name, description, dueDate));
    }

    @Override
    public void listProjects(String filter) {
        String searchText = (filter != null) ? filter.toLowerCase() : "";

        List<Project> results = this.projects.stream()
                                        .filter(p -> p.getTitle().toLowerCase().contains(searchText))
                                        .toList();

        if (results == null || results.isEmpty()) {
            view.printWarning(String.format("No projects found."));
            return;
        }

        view.printProjectList(results);
    }

    @Override
    public void showProject(String name) {
        Project project = getProjectByName(name);
        if (project == null) { return; } 

        view.printProject(project);
    }

    @Override
    public void editProject(String name, String newName, String description, LocalDateTime dueDate) {
        Project project = getProjectByName(name);
        if (project == null) { return; } 

        if (newName != null && !newName.isBlank()) {
            project.setName(newName);
        }

        if (description != null && !description.isBlank()) {
            project.setDescription(description);
        }

        if (dueDate != null) {
            project.setDueDate(dueDate);
        }

        //TODO view.printMessage();
        view.printWarning("This feature is not yet implemented.");
    }

    @Override
    public void removeProjects(String[] projectNames) {
        if (projectNames == null || projectNames.length <= 0) {
            view.printError("Specify at least one project name to remove.");
            return;
        }
        //TODO
        view.printWarning("This feature is not yet implemented.");
    }

    // private functions

    private Project getProjectByName(String name) {
        String searchText = (name != null && !name.isBlank()) ? name.toLowerCase() : null;

        if (searchText == null) {
            view.printError("Project name cannot be empty or null.");
            return null;
        }

        Project result = this.projects
                            .stream()
                            .filter(p -> p.getTitle().equalsIgnoreCase(searchText))
                            .findFirst()
                            .orElse(null);

        if (result == null) {
            view.printError(String.format("Project '%s' not found.", name));
        }
            
        return result;
    }
}
