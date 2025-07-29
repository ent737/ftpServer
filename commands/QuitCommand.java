package commands;
import java.io.PrintWriter;

public class QuitCommand implements FTPCommand {
    @Override
    public void execute(String args, BufferedReader in, PrintWriter out) {
        out.println(" Goodbye.");
    }
}
