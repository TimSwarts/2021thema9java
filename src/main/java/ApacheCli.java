import org.apache.commons.cli.*;

/**
 * The ApacheCli class manages the builds Options and parses Arguments to the WekaWrapper class. It allows the user to
 * input options when calling the java command to run the jar.
 *
 * @author Tim Swarts
 */
public class ApacheCli {
    // set class variables
    String[] arguments;
    String fileName;
    private static final String HELP = "help";
    private static final String FILE = "file-name";
    public Options options = new Options();

    public ApacheCli(String[] args){
        this.arguments = args;
        buildOptions();
        this.fileName = parseArguments();
    }

    /**
     * This function creates the options
     */
    public void buildOptions(){
        Option help = new Option("h", "help", false, "Get help manual");
        Option filePath = new Option("f", "file-name", true, "Specify path to input " +
                "csv file");
        filePath.setArgName("file");

        options.addOption(help);
        options.addOption(filePath);
    }

    /**
     * This function creates a CommandLineParser, and allows the user to call the help option or add a file name to the
     * command. If neither is given, the help will be printed along with a message.
     * @return If a file name is given, its returned so that it can be used in the WekaWrapper, this is a String value
     */
    public String parseArguments(){
        // Parsing input from the command line
        CommandLineParser parser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();

        try {
            CommandLine command = parser.parse(options, arguments);
            // Check if the user entered the HELP argument
            if (command.hasOption(HELP)){
                helpFormatter.printHelp("projectthema9-1.0.jar", options, true);
                return null;
            } else if (command.hasOption(FILE)){
                return command.getOptionValue(FILE);
            }
        } catch (ParseException e) {
            // Printing the help when something goes wrong
            System.out.println("An error occurred, please consult the below manual:");
            helpFormatter.printHelp("2021thema9-1.0.jar", options, true);
        }
        return null;
    }

}

