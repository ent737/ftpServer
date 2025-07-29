package client_handler;

import java.io.PrintWriter;

public class ShowCommands {

    // Just a helper method to print everything
    public static void printHelp(PrintWriter out) {
        out.println("User logged in. Welcome!");
        out.println("Type 'QUIT' to disconnect.");
        out.println("Type 'LIST' to list files and directories in current dir.");
        out.println("Type 'RETR' to download a file.");
        out.println("Type 'STOR' to upload a file.");
        out.println("Type 'SHOW' to see who is connected.");
        out.println("Type 'CWC' to chat with other connected clients.");
    }
}
