package commands;
import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;

public class ListCommand implements FTPCommand {
    @Override
    public void execute(String args, BufferedReader in, PrintWriter out) 
    {
        try 
        {
            File currentDir = new File("."); 
            File[] files = currentDir.listFiles();

            if (files != null) {
                out.println("Here are all of the files/directory in the current directory:");
                for (File file : files) 
                {
                    String fileType;
                    if (file.isDirectory()==true) 
                    {
                        fileType = "DIR";
                    }else
                    {
                        fileType = "FILE";
                    }
                    out.println(fileType + " " + file.getName());
                }
            out.println(" Directory send OK.");
    }   else 
    {
            out.println(" Requested file action not taken.");
    }
} catch (Exception e) {
    e.printStackTrace();
    out.println(" Internal server error.");
}

    }
}
