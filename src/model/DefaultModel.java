package src.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DefaultModel implements Model {
    private static final String SEP = "|";
    private static final String PROJECT_PREFIX = "PROJECT";
    private static final String TASK_PREFIX = "TASK";
    private static final String MEMBER_PREFIX = "MEMBER";
    private static final String PROJECT_DIVIDER = "###";
    private static final String NULL_VALUE = "null";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private final String filePath;

    public DefaultModel(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void saveProject(Project project){
        List<Project> projects = loadProjects();

        boolean found = false;
        for(int i = 0; i < projects.size(); i++) {
            if(projects.get(i).getId().equals(project.getId())){
                projects.set(i, project);
                found = true;
                break;
            }
        }
        if(!found){ 
            projects.add(project); 
        }
        writeAll(projects);
    }

    @Override
    public List<Project> loadProjects(){
        Path path = Path.of(filePath);
        if(!Files.exists(path)){ 
            return new ArrayList<>(); 
        }

        try{
            List<String> lines = Files.readAllLines(path);
            return parse(lines);
        } catch(IOException e){
            System.err.println("Error loading projects: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteProject(Project project){
        List<Project> projects = loadProjects();
        projects.removeIf(p -> p.getId().equals(project.getId()));
        writeAll(projects);
    }

    private void writeAll(List<Project> projects){
        StringBuilder sb = new StringBuilder();

        for(Project project : projects){
            sb.append(PROJECT_PREFIX)
                .append(SEP)
                .append(project.getId())
                .append(SEP)
                .append(encode(project.getTitle()))
                .append(SEP)
                .append(encode(project.getDescription()))
                .append(SEP)
                .append(encodeDate(project.getDueDate()))
                .append("\n");

            for(Task task : project.getTasks()){
                sb.append(TASK_PREFIX)
                    .append(SEP)
                    .append(encode(task.getTitle()))
                    .append(SEP)
                    .append(encode(task.getDescription()))
                    .append(SEP)
                    .append(task.getState().name())
                    .append(SEP)
                    .append(task.getPriority().name())
                    .append(SEP)
                    .append(encodeDate(task.getDueDate()))
                    .append("\n");

                for(Member member : task.getAssignees()){
                    sb.append(MEMBER_PREFIX)
                        .append(SEP)
                        .append(encode(member.getName()))
                        .append(SEP)
                        .append(encode(member.getRole()))
                        .append("\n");
                }
            }
            sb.append(PROJECT_DIVIDER).append("\n");
        }

        try{
            Files.writeString(Path.of(filePath), sb.toString());
        } catch(IOException e){
            System.err.println("Error saving projects: " + e.getMessage());
        }
    }


    private List<Project> parse(List<String> lines){
        List<Project> projects = new ArrayList<>();
        Project currentProject = null;
        Task currentTask = null;

        for(String line : lines){
            if(line.isBlank()){ 
                continue;
            }

            if(line.equals(PROJECT_DIVIDER)){
                if (currentProject != null){ 
                    projects.add(currentProject); 
                }
                currentProject = null;
                currentTask = null;
                continue;
            }

            String[] parts = line.split("\\|", -1);

            switch(parts[0]){
                case PROJECT_PREFIX -> {
                    if(parts.length < 5){ 
                        continue; 
                    }
                    currentProject = new Project( UUID.fromString(parts[1]), decode(parts[2]), decode(parts[3]), decodeDate(parts[4]) );
                    currentTask = null;
                }
                case TASK_PREFIX -> {
                    if(parts.length < 6 || currentProject == null){ 
                        continue; 
                    }
                    currentTask = new Task( decode(parts[1]), decode(parts[2]), decode(parts[3]), parts[4], decodeDate(parts[5]) );
                    currentProject.addTasks(currentTask);
                }
                case MEMBER_PREFIX -> {
                    if(parts.length < 3 || currentTask == null){ 
                        continue; 
                    }
                    currentTask.addAssignees(new Member(decode(parts[1]), decode(parts[2])));
                }
            }
        }
        return projects;
    }

    private String encode(String value){
        return (value == null || value.isBlank()) ? NULL_VALUE : value;
    }

    private String decode(String value) {
        return NULL_VALUE.equals(value) ? null : value;
    }

    private String encodeDate(LocalDateTime date){
        return (date == null) ? NULL_VALUE : date.format(FORMATTER);
    }

    private LocalDateTime decodeDate(String value){
        if(NULL_VALUE.equals(value)) { 
            return null; 
        }
        try{
            return LocalDateTime.parse(value, FORMATTER);
        } catch(Exception e){
            return null;
        }
    }
}
