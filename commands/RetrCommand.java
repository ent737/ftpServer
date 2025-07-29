package commands;
import java.io.*;

public class RetrCommand implements FTPCommand {
    @Override
    public void execute(String args, BufferedReader in, PrintWriter out) {
        if (args == null || args.isEmpty()) {
            out.println(" Syntax error in parameters or arguments.");
            return;
        }

        File file = new File(args);

        if (file.exists() && file.isFile()) {
            try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = fileReader.readLine()) != null) {
                    out.println(line);
                }
                out.println(" Transfer complete.");
            } catch (IOException e) {
                out.println(" Requested file action not taken. File read error.");
            }
        } else {
            out.println(" File not found.");
        }
    }
}
