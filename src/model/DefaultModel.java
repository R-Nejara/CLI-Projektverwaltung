package src.model;

import java.util.ArrayList;
import java.util.List;

public class DefaultModel implements Model {
    private String filePath;

    public DefaultModel(String filePath) {
        this.filePath = filePath;
    }
    
    @Override
    public void saveProject(Project project) {
        //TODO
        System.out.println("Unimplemented method 'saveProject'");
    }

    @Override
    public List<Project> loadProjects() {
        //TODO
        System.out.println("Unimplemented method 'loadProjects'");
        return new ArrayList<>();
    }

    @Override
    public void deleteProject(Project project) {
        //TODO
        System.out.println("Unimplemented method 'deleteProject'");
    }

}
