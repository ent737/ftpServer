package commands;
import java.io.BufferedReader;
import java.io.PrintWriter;

public interface FTPCommand {
    void execute(String args, BufferedReader in, PrintWriter out);
}
