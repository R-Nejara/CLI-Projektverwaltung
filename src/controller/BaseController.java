package src.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import src.model.DefaultModel;
import src.model.Model;
import src.model.Project;
import src.model.Task;
import src.view.DefaultView;
import src.view.View;

public abstract class BaseController implements Controller {
    protected final View view = new DefaultView();
    protected final Model model;
    private final List<Project> projects;

    protected  BaseController() {
        this.model = new DefaultModel("filePath")
        this.projects = this.model.loadProjects();
    }

//-------------------------------------------------------------------------
// Section: Project
//-------------------------------------------------------------------------

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
        model.saveProject(newProject);

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
        Boolean projectUpdated = false;

        if (project == null) { return; } 

        if (newName != null && !newName.isBlank() && !newName.equals(project.getTitle())) {
            project.setName(newName);
            projectUpdated = true;
        }

        if (description != null && !description.isBlank() && !description.equals(project.getDescription())) {
            project.setDescription(description);
            projectUpdated = true;
        }

        if (dueDate != null && dueDate.equals(project.getDueDate())) {
            project.setDueDate(dueDate);
            projectUpdated = true;
        }

        if (projectUpdated) {
            model.saveProject(project);
            view.printMessage("Project '%s' successfully updated.".formatted(project.getTitle()));
        } else {
            view.printWarning("Nothing was updated.");
        }        
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

//-------------------------------------------------------------------------
// Section: Task
//-------------------------------------------------------------------------

    @Override
    public void addTask(String projectName, String name, String description, String state, LocalDateTime dueDate) {
        Project project = getProjectByName(projectName);
        if (project == null) { return; }

        if (name == null || name.isBlank()) {
            view.printError("Task name cannot be empty or null.");
            return;
        }

        Task newTask = new Task(name, description, state, dueDate);
        project.addTasks(newTask);

        model.saveProject(project);
        view.printMessage(String.format("Task added [Project: %s, Name: %s, Desciption: %s, DueDate: %s]\n", projectName, name, description, dueDate));
    }

    @Override
    public void editTask(String projectName, String taskName, String newName, String description, String state, LocalDateTime dueDate) {
        Boolean projectUpdated = false;
        Project project = getProjectByName(projectName);
        if (project == null) { return; }

        Task task = getTaskByName(project, taskName);
        if (task == null) { return; } 

        if (newName != null && !newName.isBlank() && !newName.equals(task.getTitle())) {
            task.setTitle(newName);
            projectUpdated = true;
        }

        if (description != null && !description.isBlank() && !description.equals(task.getDescription())) {
            task.setDescription(description);
            projectUpdated = true;
        }

        if (state != null && !state.isBlank() && !state.equals(task.getState().toString())) {
            task.setState(state);
            projectUpdated = true;
        }

        if (dueDate != null && !dueDate.equals(task.getDueDate())) {
            task.setDueDate(dueDate);
            projectUpdated = true;
        }

        if (projectUpdated) {
            model.saveProject(project);
            view.printMessage("Task '%s' successfully updated.".formatted(task.getTitle()));
        } else {
            view.printWarning("Nothing was updated.");
        }   
    }

    @Override
    public void removeTasks(String projectName, Set<String> taskNames) {
        Project project = getProjectByName(projectName);
        Integer taskCount = project.getTasks().size();

        for (String taskName : taskNames) {
            Task task = getTaskByName(project, taskName);

            if (task == null) { continue; }
            
            project.removeTasks(task);
            view.printMessage("Successfully deleted '%s'".formatted(project.getTitle()));
        }

        if (project.getTasks().size() >= taskCount) {
            view.printWarning("No tasks deleted.");
        } else {
            model.saveProject(project);
        }
    }


//-------------------------------------------------------------------------
// Section: Assignee/Member
//-------------------------------------------------------------------------

    @Override
    public void addAssignee(String projectName, String taskName, String name, String role) {
        //TODO
        System.out.println("Unimplemented method 'addAssignee'");
    }

    @Override
    public void editAssignee(String projectName, String taskName, String memberName, String name, String role) {
        //TODO
        System.out.println("Unimplemented method 'editAssignee'");
    }

    @Override
    public void removeAssignees(String projectName, String taskName, Set<String> assigneeNames) {
        //TODO
        System.out.println("Unimplemented method 'removeAssignees'");
    }

//-------------------------------------------------------------------------
// Section: private functions
//-------------------------------------------------------------------------

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

    private Task getTaskByName(Project project, String name) {
        String searchText = (name != null && !name.isBlank()) ? name.toLowerCase() : null;

        if (project == null) { 
            view.printError("Project name cannot be empty or null.");
            return null;
        }

        if (searchText == null) {
            view.printError("Task name cannot be empty or null.");
            return null;
        }

        Task result = project.getTasks()
                            .stream()
                            .filter(p -> p.getTitle().equalsIgnoreCase(searchText))
                            .findFirst()
                            .orElse(null);

        if (result == null) {
            view.printError(String.format("Task '%s' not found.", name));
        }
            
        return result;
    }
}
