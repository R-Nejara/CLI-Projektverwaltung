package src.commands.Project;

import src.commands.BaseCommand;

public class ExportProjectCommand extends BaseCommand {
    public ExportProjectCommand() {
        super("export", "-ex", 1);
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.err.println("ERROR: usage: project export <name> <file>");
            return;
        }

        String projectName = args[0];
        String filePath = args[1];

        // TODO: Projekt anhand von projectName aus dem ProjectManager laden
        // Project project = projectManager.findByName(projectName);
        // new ProjectExporter().export(project, filePath);

        System.out.printf("export_project(name: %s, file: %s)\n", projectName, filePath);
    }

    @Override
    public String toString() {
        return "\t" + this.getKey().toUpperCase() + ": (project|-p) (export|-ex) <name> <file>";
    }
}