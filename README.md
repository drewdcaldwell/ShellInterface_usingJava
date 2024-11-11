# ShellInterface_usingJava
implement a simple shell interface in Java that accepts user commands and executes them in a separate process, similar to a UNIX shell.
MyShell - Custom Command-Line Shell

Steps to Run the Program:

1. Clone or Download the Code:
   - Get the MyShell.java file and place it in your working directory.

2. Compile the Java Code:
   - Open your terminal or command prompt and run:
     javac MyShell.java

3. Run the Shell:
   - After compiling, run the program using:
     java MyShell

4. Shell Prompt:
   - The program will display a prompt "mysh>", waiting for user input.

Operations Supported:

1. Run Commands:
   - You can run system commands like `ls`, `cat`, `dir`, etc.
   - Example:
     mysh> ls

2. Change Directory (cd):
   - Use `cd` to change the current working directory.
   - Example:
     mysh> cd music
   - If no directory is specified or if you type `cd ~`, it changes to the home directory.

3. View Command History:
   - Type `history` to see the list of all commands entered.
   - Example:
     mysh> history

4. Re-run Previous Command (!!):
   - Type `!!` to re-run the last command from history.
   - Example:
     mysh> !!

5. Exit the Shell:
   - Type `exit` to exit the shell.
   - Example:
     mysh> exit

How the Code Works:

1. Main Loop:
   - The program runs in a loop, waiting for user input ("mysh> ").

2. Command Parsing:
   - The input command is split into parts using whitespace as a separator.

3. Process Execution:
   - If the command is not `cd`, a `ProcessBuilder` is used to execute the command in the system shell.

4. Changing Directory (cd):
   - The `cd` command is handled directly within the program, and it changes the working directory for the shell.

5. Command History:
   - Each command entered is saved in a history list. The `history` command displays all previous commands, and `!!` re-runs the last command.

Troubleshooting:

- `cd` command not working: Ensure that the directory exists. If invalid, an error will be shown.
- Command not running: Make sure the command is valid and available in the system's `PATH`.
