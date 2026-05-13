package src.model;

import java.util.ArrayList;
import java.util.List;

public class DefaultModel implements Model {
    @Override
    public void saveProject(Project project) {
        //TODO
        System.out.println("Unimplemented method 'exportProject'");
    }

    @Override
    public List<Project> loadProjects() {
        //TODO
        System.out.println("Unimplemented method 'importProjects'");
        return new ArrayList<>();
    }

    @Override
    public void deleteProject(Project project) {
        //TODO
        System.out.println("Unimplemented method 'deleteProject'");
    }

}
