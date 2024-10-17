import java.io.*;
import java.util.*;

public class MyShell {
    public static void main(String[] args) throws java.io.IOException {
        // commandLine allows us to see what the user will write in the commandLine
        String commandLine;
        // Buffered reader allows us to see what commandLine is
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        // currentDir is needed to know what directory we are currently in
        File currentDir = new File(System.getProperty("user.dir"));
        // history will allow us to keep track of what has been ran on the program
        List<String> history = new ArrayList<>();

        while (true) {
            // Read what the user entered
            System.out.print("mysh>");
            commandLine = console.readLine();

            // If the user entered a return, just loop again
            if (commandLine.equals("")) {
                continue;
            }

            // Add command to history
            history.add(commandLine);

            // Parse the input to obtain the command and any parameters
            String[] commandArray = commandLine.split(" ");
            List<String> command = new ArrayList<>(Arrays.asList(commandArray));

            // Handle the 'cd' command
            if (command.get(0).equals("cd")) {
                if (command.size() == 1) {
                    currentDir = new File(System.getProperty("user.home"));
                } else {
                    File newDir = new File(currentDir, command.get(1));
                    if (newDir.exists() && newDir.isDirectory()) {
                        currentDir = newDir;
                    } else {
                        System.out.println("Invalid directory");
                    }
                }
                continue;
            }

            // Handle the 'history' command
            if (command.get(0).equals("history")) {
                for (int i = 0; i < history.size(); i++) {
                    System.out.println(i + " " + history.get(i));
                }
                continue;
            }

            // For Windows, add "cmd" and "/c" to the command list
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                command.add(0, "cmd");
                command.add(1, "/c");
            }

            // Create a ProcessBuilder object
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(currentDir);

            try {
                // Start the process
                Process process = pb.start();

                // Obtain the output stream
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;

                // Output the contents returned by the command
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                // Wait for the process to complete
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                System.out.println("Error executing command: " + e.getMessage());
            }
        }
    }
}
