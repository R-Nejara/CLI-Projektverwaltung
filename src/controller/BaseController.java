package src.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import src.model.DefaultModel;
import src.model.Member;
import src.model.Model;
import src.model.Project;
import src.model.Task;
import src.view.View;

public abstract class BaseController implements Controller {
    protected final View view;
    protected final Model model;
    private final List<Project> projects;

    protected  BaseController(View view) {
        this.view = view;
        this.model = new DefaultModel(System.getProperty("user.home") + "/.cli_projektverwaltung.txt");
        this.projects = this.model.loadProjects();
    }

//-------------------------------------------------------------------------
// Section: Project
//-------------------------------------------------------------------------

    /**
     * Adds a new project to the system if all validation rules are met.
     * The project is registered in the local collection and persistently saved via the model.
     * 
     * If the name and description validation fails, an error message is printed via the view, and the 
     * project will not be created.
     *
     * @param name        the unique name of the new project
     * @param description a brief description of the project (optional, may be null)
     * @param dueDate     the deadline of the project (optional, may be null)
     */
    @Override
    public void addProject(String name, String description, LocalDateTime dueDate) {
        if (name == null || name.isBlank()) {
            view.printError("Project name cannot be empty or null.");
            return;
        } else if (!isNameUnique(name, getProjectNames())) {
            view.printError("A project with the name '%s' already exists.".formatted(name));
            return;
        } else if (!nameIsValid(name)) {
            view.printError("Project name must start with a letter and cannot contain the '|' character.");
            return;
        } else if (description != null && description.contains("|")) {
            view.printError("Project description cannot contain the '|' character.");
            return;
        }

        Project newProject = new Project(name, description, dueDate);
        this.projects.add(newProject);
        model.saveProject(newProject);
        view.printMessage("Project added [Name: %s, Description: %s, DueDate: %s]".formatted(name, description, dueDate));
    }

    /**
     * Lists all projects in the system, optionally filtered by a search term.
     *
     * @param filter the search term to filter projects by (optional, may be null)
     */
    @Override
    public boolean listProjects(String filter) {
        String searchText = (filter != null) ? filter.toLowerCase() : "";

        List<Project> results = this.projects.stream()
                                        .filter(p -> p.getTitle().toLowerCase().contains(searchText))
                                        .toList();

        if (results == null || results.isEmpty()) {
            view.printWarning("No projects found.");
            return false;
        }

        view.printProjectList(results);
        return true;
    }

    /**
     * Displays the details of a specific project.
     *
     * @param name the name of the project to display
     */
    @Override
    public void showProject(String name) {
        Project project = getProjectByNameOrNumber(name);
        if (project == null) { return; } 

        view.printProject(project);
    }

    /**
     * Edits the details of an existing project if all validation rules are met.
     * The project is updated in the local collection and persistently saved via the model.
     * 
     * If the name and description validation fails, an error message is printed via the view, and the 
     * project will not be updated.
     *
     * @param name        the name of the project to edit
     * @param newName     the new unique name for the project (optional, may be null)
     * @param description a new brief description of the project (optional, may be null)
     * @param dueDate     a new deadline for the project (optional, may be null)
     */
    @Override
    public void editProject(String name, String newName, String description, LocalDateTime dueDate) {
        Project project = getProjectByNameOrNumber(name);
        boolean projectUpdated = false;

        if (project == null) { return; } 

        if (newName != null && !newName.isBlank()) {
            if (!isNameUnique(newName, getProjectNames())) {
                view.printError("A project with the name '%s' already exists.".formatted(newName));
                return;
            } else if (!nameIsValid(newName)) {
                view.printError("Project name must start with a letter and cannot contain the '|' character.");
                return;
            } else if (!newName.equals(project.getTitle())) {
                project.setTitle(newName);
                projectUpdated = true;
            } 
        }
            
        if (description != null && description.contains("|")) {
            view.printError("Project description cannot contain the '|' character.");
            return;
        } else if (description != null && !description.equals(project.getDescription())) {
            project.setDescription(description);
            projectUpdated = true;
        }

        if (dueDate != null && !dueDate.equals(project.getDueDate())) {
            project.setDueDate(dueDate);
            projectUpdated = true;
        }

        if (projectUpdated) {
            model.saveProject(project);
            view.printMessage("Project '%s' successfully updated.".formatted(name));
        } else {
            view.printWarning("Nothing was updated.");
        }        
    }

    /**
     * Removes the specified projects from the system.
     *
     * @param projectNames a set of project names to remove
     */
    @Override
    public void removeProjects(Set<String> projectNames) {
        int projectCount = this.projects.size();

        if (this.projects.isEmpty()) {
            view.printError("No Projects available.");
            return;
        }

        if (projectNames == null || projectNames.size() <= 0) {
            view.printError("Specify at least one project name to remove.");
            return;
        }

        for (String projectName : projectNames) {
            Project project = getProjectByNameOrNumber(projectName);

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

    /**
     * Adds a new task to the specified project.
     *
     * if the project is not found or the task name validation fails, an error message is printed via the view, and the task will not be created.
     * @param projectName the name of the project to add the task to
     * @param name        the name of the task
     * @param description a brief description of the task
     * @param state       the current state of the task
     * @param priority    the priority of the task
     * @param dueDate     the deadline for the task
     */
    @Override
    public void addTask(String projectName, String name, String description, String state, String priority, LocalDateTime dueDate) {
        Project project = getProjectByNameOrNumber(projectName);
        if (project == null) { return; }

        if (name == null || name.isBlank()) {
            view.printError("Task name cannot be empty or null.");
            return;
        } else if (!isNameUnique(name, getTaskNames(project))) {
            view.printError("A task with the name '%s' already exists in project '%s'.".formatted(name, projectName));
            return;
        } else if (!nameIsValid(name)) {
            view.printError("Task name must start with a letter and cannot contain the '|' character.");
            return;
        }

        Task newTask = new Task(name, description, state, priority, dueDate);
        project.addTasks(newTask);
        model.saveProject(project);
        view.printMessage("Task added [Project: %s, Name: %s, Description: %s, State: %s, Priority: %s, DueDate: %s]".formatted(projectName, name, description, state, priority, dueDate));
    }

    /**
     * Lists all tasks in the specified project, optionally filtered by a search term.
     *
     * @param projectName the name of the project containing the tasks
     * @param filter the search term to filter tasks by (optional, may be null)
     */
    @Override
    public void listTasks(String projectName,String filter) {
        String searchText = (filter != null) ? filter.toLowerCase() : "";
        Project project = getProjectByNameOrNumber(projectName);
        if (project == null) { return; }

        List<Task> results = project.getTasks().stream()
                                        .filter(t -> t.getTitle().toLowerCase().contains(searchText))
                                        .toList();

        if (results == null || results.isEmpty()) {
            view.printWarning("No tasks found.");
            return;
        }

        view.printTaskList(results);
    }

    /**
     * Edits the details of an existing task if all validation rules are met.
     * The task is updated in the local collection and persistently saved via the model.
     *
     * If the name validation fails, an error message is printed via the view, and the
     * task will not be updated.
     *
     * @param projectName the name of the project containing the task
     * @param taskName    the name of the task to edit
     * @param newName       the new unique name for the task (optional, may be null)
     * @param description   a new brief description of the task (optional, may be null)
     * @param state         the new state of the task (optional, may be null)
     * @param priority      the new priority of the task (optional, may be null)
     * @param dueDate       the new deadline for the task (optional, may be null)
     */
    @Override
    public void editTask(String projectName, String taskName, String newName, String description, String state, String priority, LocalDateTime dueDate) {
        boolean projectUpdated = false;
        Project project = getProjectByNameOrNumber(projectName);
        if (project == null) { return; }

        Task task = getTaskByNameOrNumber(project, taskName);
        if (task == null) { return; } 

        if (newName != null && !newName.isBlank()) {
            if (!isNameUnique(newName, getAssigneeNames(task))) {
                view.printError("A member with the name '%s' already exists in task '%s'.".formatted(newName, taskName));
            return;
            } else if (!nameIsValid(newName)) {
                view.printError("Task name must start with a letter and cannot contain the '|' character.");
                return;
            } else if (!newName.equals(task.getTitle())) {
                task.setTitle(newName);
                projectUpdated = true;
            }
        }


        if (description != null && !description.isBlank() && !description.equals(task.getDescription())) {
            task.setDescription(description);
            projectUpdated = true;
        }

        if (state != null && !state.isBlank() && !state.equals(task.getState().toString())) {
            task.setState(state);
            projectUpdated = true;
        }

        if (priority != null && !priority.isBlank() && !priority.equals(task.getPriority().toString())) {
            task.setPriority(priority);
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

    /**
     * Removes the specified tasks from the given project.
     *
     * @param projectName the name of the project containing the tasks
     * @param taskNames   a set of task names to remove
     */
    @Override
    public void removeTasks(String projectName, Set<String> taskNames) {
        Project project = getProjectByNameOrNumber(projectName);
        if (project == null) { return; }
        int taskCount = project.getTasks().size();

        for (String taskName : taskNames) {
            Task task = getTaskByNameOrNumber(project, taskName);

            if (task == null) { continue; }
            
            project.removeTasks(task);
            view.printMessage("Successfully deleted '%s'".formatted(task.getTitle()));
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

    /**
     * Adds a new assignee to the specified task.
     *
     * @param projectName the name of the project containing the task
     * @param taskName    the name of the task to add the assignee to
     * @param name          the name of the assignee
     * @param role          the role of the assignee
     */
    @Override
    public void addAssignee(String projectName, String taskName, String name, String role) {
        Project project = getProjectByNameOrNumber(projectName);
        if (project == null) { return; }

        Task task = getTaskByNameOrNumber(project, taskName);
        if (task == null) { return; }

        if (name != null && name.isBlank()) {
            if (!isNameUnique(name, getAssigneeNames(task))) {
                view.printError("A member with the name '%s' already exists in task '%s'.".formatted(name, taskName));
                return;
            } else if (!nameIsValid(name)) {
                view.printError("Member name must start with a letter and cannot contain the '|' character.");
                return;
            }
            view.printError("Member name cannot be empty.");
            return;
        }

        Member newMember = new Member(name, role);
        task.addAssignees(newMember);
        model.saveProject(project);
        view.printMessage("Assignee added [Project: %s, Task: %s, Name: %s, Role: %s]".formatted(projectName, taskName, name, role));
    }

    /**
     * Lists all members in the specified task, optionally filtered by a search term.
     *
     * @param projectName the name of the project containing the task
     * @param taskName the name of the task containing the assignees
     * @param filter the search term to filter members by (optional, may be null)
     */
    @Override
    public void listMembers(String projectName, String taskName, String filter) {
        String searchText = (filter != null) ? filter.toLowerCase() : "";
        Project project = getProjectByNameOrNumber(projectName);
        if (project == null) { return; }

        Task task = getTaskByNameOrNumber(project, taskName);
        if (task == null) { return; }

        List<Member> results = task.getAssignees().stream()
                                    .filter(m -> m.getName().toLowerCase().contains(searchText))
                                    .toList();

        if (results == null || results.isEmpty()) {
            view.printWarning("No members found.");
            return;
        }

        view.printMemberList(results);
    }

    /**
     * Edits the details of an existing assignee if all validation rules are met.
     * The assignee is updated in the local collection and persistently saved via the model.
     *
     * If the name validation fails, an error message is printed via the view, and the
     * assignee will not be updated.
     *
     * @param projectName the name of the project containing the task
     * @param taskName    the name of the task containing the assignee
     * @param memberName  the name of the assignee to edit
     * @param name          the new unique name for the assignee (optional, may be null)
     * @param role          the new role for the assignee (optional, may be null)
     */
    @Override
    public void editAssignee(String projectName, String taskName, String memberName, String name, String role) {
        boolean projectUpdated = false;
        Project project = getProjectByNameOrNumber(projectName);
        if (project == null) { return; }

        Member member = getAssigneeByNameOrNumber(project, taskName, memberName);
        if (member == null) { return; }

        if (name != null && !name.isBlank() && !name.equals(member.getName())) {
            member.setName(name);
            projectUpdated = true;
        }

        if (role != null && !role.isBlank() && !role.equals(member.getRole())) {
            member.setRole(role);
            projectUpdated = true;
        }

        if (projectUpdated) {
            model.saveProject(project);
            view.printMessage("Member '%s' successfully updated.".formatted(member.getName()));
        } else {
            view.printWarning("Nothing was updated.");
        }   
    }

    /**
     * Removes the specified assignees from the given task.
     *
     * @param projectName the name of the project containing the task
     * @param taskName    the name of the task containing the assignees
     * @param assigneeNames a set of assignee names to remove
     */
    @Override
    public void removeAssignees(String projectName, String taskName, Set<String> assigneeNames) {
        Project project = getProjectByNameOrNumber(projectName);
        if (project == null) { return; }

        Task task = getTaskByNameOrNumber(project, taskName);
        if (task == null) { return; }

        int taskCount = task.getAssignees().size();

        for (String assigneeName : assigneeNames) {
            Member member = getAssigneeByNameOrNumber(project, taskName, assigneeName);

            if (member == null) { continue; }
            
            task.removeAssignees(member);
            view.printMessage("Successfully deleted '%s'".formatted(member.getName()));
        }

        if (task.getAssignees().size() >= taskCount) {
            view.printWarning("No members deleted.");
        } else {
            model.saveProject(project);
        }
    }

//-------------------------------------------------------------------------
// Section: private functions
//-------------------------------------------------------------------------

    private List<String> getProjectNames() {
        return this.projects.stream()
                .map(p -> p.getTitle())
                .toList();
    }

    private List<String> getTaskNames(Project project) {
        return project.getTasks().stream()
                .map(t -> t.getTitle())
                .toList();
    }

    private List<String> getAssigneeNames(Task task) {
        return task.getAssignees().stream()
                .map(a -> a.getName())
                .toList();
    }

    private boolean isNameUnique(String name, List<String> names) {
        if (name == null || name.isBlank()) { return false; }

        return names.stream().noneMatch(n -> n.equalsIgnoreCase(name));
    }

    private boolean nameIsValid(String input) {
        if (input == null || input.isBlank()) { return false; }

        return Character.isLetter(input.charAt(0)) && !input.contains("|");
    }

    private Project getProjectByNameOrNumber(String name) {
        if (name == null || name.isBlank()) {
            view.printError("Project name cannot be empty or null.");
            return null;
        }

        if (name.matches("[1-9]\\d*")) {
            int projectNumber = Integer.parseInt(name);
            if (projectNumber > 0 && projectNumber <= this.projects.size()) {
                return this.projects.get(projectNumber - 1);
            }
        }

        Project result = this.projects.stream()
                            .filter(p -> p.getTitle().equalsIgnoreCase(name))
                            .findFirst()
                            .orElse(null);

        if (result == null) {
            view.printError("Project '%s' not found.".formatted(name));
        }
            
        return result;
    }

    private Task getTaskByNameOrNumber(Project project, String name) {
        if (project == null) { 
            view.printError("Project cannot be null.");
            return null;
        }

        if (name == null || name.isBlank()) {
            view.printError("Task name cannot be empty or null.");
            return null;
        }

        if (name.matches("[1-9]\\d*")) {
            int taskNumber = Integer.parseInt(name);
            if (taskNumber > 0 && taskNumber <= project.getTasks().size()) {
                return project.getTasks().get(taskNumber - 1);
            }
        }

        Task result = project.getTasks().stream()
                            .filter(p -> p.getTitle().equalsIgnoreCase(name))
                            .findFirst()
                            .orElse(null);

        if (result == null) {
            view.printError("Task '%s' not found.".formatted(name));
        }
            
        return result;
    }

    private Member getAssigneeByNameOrNumber(Project project, String taskName, String name) {
        Task task = getTaskByNameOrNumber(project, taskName);
        if (task == null) { return null; }

        if (name == null || name.isBlank()) {
            view.printError("Assignee name cannot be empty or null.");
            return null;
        }

        if (name.matches("[1-9]\\d*")) {
            int assigneeNumber = Integer.parseInt(name);
            if (assigneeNumber > 0 && assigneeNumber <= task.getAssignees().size()) {
                return task.getAssignees().get(assigneeNumber - 1);
            }
        }

        Member result = task.getAssignees().stream()
                            .filter(p -> p.getName().equalsIgnoreCase(name))
                            .findFirst()
                            .orElse(null);

        if (result == null) {
            view.printError("Assignee '%s' not found.".formatted(name));
        }

        return result;
    }
}