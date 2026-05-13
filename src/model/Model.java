package src.model;

import java.util.List;

public interface Model {
    public void saveProject(Project project);
    public List<Project> loadProjects();
    public void deleteProject(Project project);
}
