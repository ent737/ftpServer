package commands;
import java.io.*;

public class StorCommand implements FTPCommand {
    
    public void execute(String args, BufferedReader in, PrintWriter out) {
        if (args == null || args.isEmpty()) {
            out.println(" Syntax error in parameters or arguments.");
            return;
        }

        File file = new File(args);

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file))) {
            out.println(" File status okay. Ready to receive.");
            String line;
            while (!(line = in.readLine()).equals("EOF")) { // Use "EOF" to signal end of file
                fileWriter.write(line);
                fileWriter.newLine();
            }
            out.println(" Transfer complete.");
        } catch (IOException e) {
            out.println(" Requested file action not taken. File write error.");
        }
    }
}
