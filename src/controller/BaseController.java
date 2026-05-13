package src.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import src.model.DefaultModel;
import src.model.Model;
import src.model.Project;
import src.view.DefaultView;
import src.view.View;

public abstract class BaseController implements Controller {
    protected final View view = new DefaultView();
    protected final Model model = new DefaultModel();
    private final List<Project> projects;

    protected  BaseController() {
        this.projects = model.importProjects();

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
        model.exportProject(newProject);

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
        model.exportProject(project);
    }

    @Override
    public void removeProjects(Set<String> projectNames) {
        Integer projectCount = this.projects.size();

        if (this.projects.isEmpty()) {
            view.printError("No Projects available.");
            return;
        }

        if (projectNames == null || projectNames.size() <= 0) {
            view.printError("Specify at least one project name to remove.");
            return;
        }

        for (String projectName : projectNames) {
            Project project = getProjectByName(projectName);
            if (project == null) { continue; }
            this.projects.remove(project);
            model.deleteProject(project);
            view.printMessage("Successfully deleted '%s'".formatted(project.getTitle()));
        }

        if (this.projects.size() >= projectCount) {
            view.printWarning("No projects deleted.");
        }
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
