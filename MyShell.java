import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyShell {
    private static File currentDirectory = new File(System.getProperty("user.dir")); // Start in the user's current directory
    private static List<String> commandHistory = new ArrayList<>(); // To store history of commands

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("mysh> ");
            String input = scanner.nextLine();

            // Exit on command "exit"
            if (input.trim().equalsIgnoreCase("exit")) {
                System.out.println("Exiting shell.");
                break;
            }

            // If user enters "history", show the command history
            if (input.trim().equals("history")) {
                printHistory();
                continue; // Skip further processing and wait for next input
            }

            // If user enters "!!", run the previous command from history
            if (input.trim().equals("!!")) {
                if (commandHistory.isEmpty()) {
                    System.err.println("Error: No previous command to run.");
                    continue; // Skip further processing and wait for next input
                }
                // Get the last command from history
                input = commandHistory.get(commandHistory.size() - 1);
                System.out.println("Running previous command: " + input);
            }

            // Add the command to history
            commandHistory.add(input);

            // Split the input command into a list of strings
            String[] parts = input.split("\\s+");
            List<String> command = new ArrayList<>();

            // Check if it's the "cd" command
            if (parts[0].equalsIgnoreCase("cd")) {
                handleCdCommand(parts);
                continue; // Skip to next iteration for the next command
            }

            // Create the command for ProcessBuilder
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                command.add("cmd");
                command.add("/c");
            }

            for (String part : parts) {
                command.add(part);
            }

            // Create ProcessBuilder with the current directory
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.directory(currentDirectory); // Set the current working directory for the process

            try {
                Process process = processBuilder.start();

                // Read the output from the process
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                // Wait for the process to complete
                process.waitFor();

            } catch (IOException e) {
                System.err.println("Error: Invalid command or command not found.");
            } catch (InterruptedException e) {
                System.err.println("Error: Process was interrupted.");
                Thread.currentThread().interrupt();
            }
        }

        scanner.close();
    }

    // Handle the "cd" command
    private static void handleCdCommand(String[] parts) {
        if (parts.length == 1 || parts[1].equals("~")) {
            // If no argument or `cd ~`, change to the user's home directory
            currentDirectory = new File(System.getProperty("user.home"));
        } else {
            // Change to the specified directory (relative or absolute path)
            File newDirectory = new File(parts[1]);
            if (newDirectory.isDirectory()) {
                currentDirectory = newDirectory;
            } else {
                System.err.println("Error: Invalid directory.");
            }
        }
        // Print the current directory after change
        System.out.println("Current directory is now: " + currentDirectory.getAbsolutePath());
    }

    // Print the command history
    private static void printHistory() {
        if (commandHistory.isEmpty()) {
            System.out.println("No commands in history.");
        } else {
            for (int i = 0; i < commandHistory.size(); i++) {
                System.out.println(i + " " + commandHistory.get(i));
            }
        }
    }
}
