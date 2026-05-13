package src.model;

import java.util.List;

public interface Model {
    public void exportProject(Project project);
    public List<Project> importProjects();
    public void deleteProject(Project project);
}
