package commands.chatwithclient;
import commands.FTPCommand; 
import java.io.BufferedReader;
import java.io.PrintWriter;

public class ShowConnected implements FTPCommand {

    @Override
    public void execute(String args, BufferedReader in, PrintWriter out) {
        out.println("Connected users: ");
        synchronized(main.FTPServer.sync) 
        {
            for (String key : main.FTPServer.listofusers.keySet()) 
            {
                out.println(key );
            }
        }
    }
}
