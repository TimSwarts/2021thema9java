import org.apache.commons.cli.*;

public class ApacheCli {
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

    public void buildOptions(){
        Option help = new Option("h", "help", false, "Get help manual");
        Option filePath = new Option("f", "file-name", true, "Specify path to input " +
                "csv file");
        filePath.setArgName("file");

        options.addOption(help);
        options.addOption(filePath);
    }

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

