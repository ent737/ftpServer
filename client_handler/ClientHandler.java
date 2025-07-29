package client_handler;

import commands.*;
import commands.chatwithclient.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import main.FTPServer;

public class ClientHandler implements Runnable {
    
    private Socket clientSocket;
    private PrintWriter out;      
    private static final Map<String, FTPCommand> commands = new HashMap<>();
    
    static {
        commands.put("LIST", new ListCommand());
        commands.put("RETR", new RetrCommand());
        commands.put("STOR", new StorCommand());
        commands.put("QUIT", new QuitCommand());
        commands.put("SHOW", new ShowConnected());
        commands.put("CWC", new CwcCommand());
    }

    // Constructor
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientIP = clientSocket.getInetAddress().getHostAddress();

            out.println("Welcome to Simple FTP Server!");

            int authResult = -1;
            try {
                authResult = FTPServer.authManager.isAuthenticated(clientIP, in, out);
            } catch (IOException e) {
                e.printStackTrace();
                out.println("An error occurred during authentication. Goodbye.");
                return;
            }

            if (authResult == -1) {
                out.println("Authentication failed. Goodbye.");
                return;  // End this handler if auth fails
            }
            ShowCommands.printHelp(out);// Show available commands

            String clientCommand;
            while ((clientCommand = in.readLine()) != null) {
                System.out.println("Received: " + clientCommand + " from " + clientIP);

                String[] parts = clientCommand.split(" ", 2);
                String command = parts[0].toUpperCase();
                String args = (parts.length > 1) ? parts[1] : null;


                FTPCommand ftpCommand = commands.get(command);

                if (ftpCommand != null) {
                    ftpCommand.execute(args, in, out);
                    if (command.equals("QUIT")) {
                        break;
                    }
                } else {
                    out.println("Command not implemented.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Remove this ClientHandler from the server's list
            // so it is no longer referenced after disconnect
            String clientIP = clientSocket.getInetAddress().getHostAddress();
            synchronized (FTPServer.sync) {
                FTPServer.listofusers.remove(clientIP);
            }

            System.out.println("Client " + clientIP + " disconnected and removed from listofusers.");
            
        }
    
    }

    // Method to send a message to this client
    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }
}
