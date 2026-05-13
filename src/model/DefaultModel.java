package src.model;

import java.util.ArrayList;
import java.util.List;

public class DefaultModel implements Model {
    @Override
    public void exportProject(Project project) {
        //TODO
        System.out.println("Unimplemented method 'exportProject'");
    }

    @Override
    public List<Project> importProjects() {
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
