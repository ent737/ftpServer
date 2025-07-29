package commands;
import java.io.File;
import java.io.PrintWriter;

public class HandleCwdCommand {
    private File currentDirectory;

    public HandleCwdCommand(File currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    public void handleCwdCommand(String clientCommand, PrintWriter out) {
        String[] parts = clientCommand.split(" ", 2);
        if (parts.length < 2) {
            out.println("501 Syntax error in parameters or arguments.");
            return;
        }

        String newDir = parts[1];
        File targetDir = new File(currentDirectory, newDir);

        if (targetDir.exists() && targetDir.isDirectory()) {
            currentDirectory = targetDir;
            out.println("250 Directory successfully changed.");
        } else {
            out.println("550 Failed to change directory. Directory does not exist.");
        }
    }
}