/*
This class is the Main.java of the application. It makes a new
CommandLineInterface, and then we call the startUI() method of
the cli in order to use the command line interface to use the app.
 */
public class Main {
    public static void main(String[] args) {
        // New object named cli from the CommandLineInterface.
        CommandLineInterface cli = new CommandLineInterface();
        // Call the method .startUI() in order to run the interface.
        cli.startUI();
    }
}
