package main;
import java.io.*;
import java.util.HashMap;

public class AuthenticationManager 
{
    private final HashMap<String, String> userDatabase = new HashMap<>(); 

    public int isAuthenticated(String clientIP, BufferedReader in, PrintWriter out) throws IOException 
    {
        String admin_username = "admin";
        String admin_password = "admin";

        if (userDatabase.containsKey(clientIP)) 
        {
            out.println(" Username required.");
            String username = in.readLine();
            out.println(" Password required.");
            String password = in.readLine();
            if(userDatabase.get(clientIP).equals(admin_username + ":" + admin_password))
            {
                out.println(" Welcome Admin! You are now logged in.");
                return 1;
            }
            if (userDatabase.get(clientIP).equals(username + ":" + password)) 
            {
                return 0;
            } else 
            {
                out.println(" Invalid username or password.");
                return -1; 
            }
        } else 
        {
            out.println("Welcome! Please register to continue.");
            out.println(" Enter a username:");
            String username = in.readLine();

            out.println(" Enter a password:");
            String password = in.readLine();
            if (username == admin_username && password == admin_password)
                {
                    out.println(" Welcome Admin! You are now logged in.");
                    userDatabase.put(clientIP, admin_username + ":" + admin_password);
                    return 1;
                }
            userDatabase.put(clientIP, username + ":" + password);

            out.println(" Registration successful. You are now logged in.");
            return 0; 
        }
    }
}
