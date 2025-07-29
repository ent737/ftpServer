package main;

import client_handler.ClientHandler;
import java.io.*;
import java.net.*;
import java.util.HashMap;

public class FTPServer {
    public static final Object sync = new Object();
    public static final AuthenticationManager authManager = new AuthenticationManager();
    public static final HashMap<String, ClientHandler> listofusers = new HashMap<>();

    public static void main(String[] args) {
        int port = 2121; 
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("FTP Server started. Listening on port " + port);

            while (true) {

                Socket clientSocket = serverSocket.accept();

                String clientIP = clientSocket.getInetAddress().getHostAddress();
                
                System.out.println("Client connected: " + clientIP);
                
                ClientHandler handler = new ClientHandler(clientSocket);

                Thread t = new Thread(handler);
                t.start();

                synchronized (sync) {
                    listofusers.put(clientIP, handler);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
