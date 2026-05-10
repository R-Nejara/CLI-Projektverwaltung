package src.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ProjectExporter {

    public void export(Project project, String filePath) throws IOException {
        String content = buildText(project);
        Files.writeString(Path.of(filePath), content);
    }

    private String buildText(Project project) {
        StringBuilder sb = new StringBuilder();

        sb.append("PROJECT: ").append(project.getTitle()).append("\n");
        sb.append("Description: ").append(nullOr(project.getDescription(), "-")).append("\n");
        sb.append("Due Date: ").append(nullOr(project.getDueDate(), "-")).append("\n");

        List<Task> tasks = project.getTasks();
        sb.append("\nTASKS (").append(tasks.size()).append("):\n");

        if (tasks.isEmpty()) {
            sb.append("  No tasks.\n");
        } else {
            for (Task task : tasks) {
                sb.append("\n  [").append(task.getState()).append("] ").append(task.getTitle()).append("\n");
                sb.append("    Description: ").append(nullOr(task.getDescription(), "-")).append("\n");
                sb.append("    Due Date:    ").append(nullOr(task.getDueDate(), "-")).append("\n");
            }
        }

        return sb.toString();
    }

    private String nullOr(Object value, String fallback) {
        return value != null ? value.toString() : fallback;
    }
}