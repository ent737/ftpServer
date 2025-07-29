package commands.chatwithclient;

import commands.FTPCommand;
import main.FTPServer;
import client_handler.ClientHandler;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Map;

public class CwcCommand implements FTPCommand {

    @Override
    public void execute(String args, BufferedReader in, PrintWriter out) {
        /*
         * Expected usage: 
         *   CWC <num_clients> <IP_1> <IP_2> ... <IP_n> <message>
         */

        if (args == null || args.trim().isEmpty()) {
            out.println("Usage: CWC <num_clients> <IP1> <IP2> ... <message>");
            return;
        }

        String[] splitArgs = args.split("\\s+");
        if (splitArgs.length < 2) {
            out.println("Usage: CWC <num_clients> <IP1> ... <message>");
            return;
        }

        int numClients;
        try {
            numClients = Integer.parseInt(splitArgs[0]);
        } catch (NumberFormatException e) {
            out.println("Usage: CWC <num_clients> <IP1> ... <message>");
            return;
        }

        if (splitArgs.length < (1 + numClients + 1)) {
            out.println("Insufficient arguments. Provide the IPs and the message.");
            return;
        }

        String[] targetIps = new String[numClients];
        for (int i = 0; i < numClients; i++) {
            targetIps[i] = splitArgs[i + 1];
        }

        StringBuilder sb = new StringBuilder();
        for (int i = (1 + numClients); i < splitArgs.length; i++) {
            sb.append(splitArgs[i]).append(" ");
        }
        String message = sb.toString().trim();

        out.println("sending message to " + numClients + " client(s).");

        for (String ip : targetIps) {
            ClientHandler handler = null;
            synchronized (FTPServer.sync) {
                handler = FTPServer.listofusers.get(ip);
            }
            if (handler != null) {
                handler.sendMessage("Message from CWC command: " + message);
                out.println("Message sent to " + ip);
            } else {
                out.println("Client with IP " + ip + " not found or not connected.");
            }
        }
    }
}
