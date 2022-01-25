import weka.classifiers.AbstractClassifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;


import java.io.IOException;


/**
 * WekaWrapper is the main class of the program, it contains methods that for loading the input file and running a
 * saved Naive Bayes model to classify this file. Which file is loaded in is determined by the terminal input, handled
 * by yhr ApacheCli class.
 *
 * @author Tim Swarts
 */
public class WekaWrapper {
    /**
     * The main function creates a new instance of the WekaWrapper and the ApacheCli class.
     * It then fetches the name of the input file from the apacheCli object and runs the start function,
     * starting the classification.
     * @param args: String array, The command line arguments
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        // Initiate new objects
        WekaWrapper runWeka = new WekaWrapper();
        ApacheCli apacheCli = new ApacheCli(args);
        // Fetch input file name from command line
        String inputFile = apacheCli.fileName;
        // If none given, exit
        if (inputFile == null){
            inputFile = "breastcancer_model_input.arff";
            // System.exit(0);
        }
        // Start running wrapper
        runWeka.start(inputFile);
    }

    /**
     * This function controls most of the flow of the script. It loads in the classifier, then fetches the instances in
     * the input file using the loadFile function, and passes these on to the classifyNewInstance function.
     * @param inputFile: String value, the name of the input file that contains to be classified instances.
     */
    private void start(String inputFile) {
        try {
            AbstractClassifier fromFile = loadClassifier();
            Instances unknownInstances = loadFile(inputFile);
            System.out.println(unknownInstances.getClass());
            System.out.println("\nunclassified unknownInstances = \n" + unknownInstances);
            classifyNewInstance(fromFile, unknownInstances);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This function uses a weka classifier and to classify and then print new instances.
     * @param classifier: AbstractClassifier object, classifies the new instances.
     * @param unknownInstances: Instances object, contains the instances to be classified, pulled from the input file.
     */
    private void classifyNewInstance(AbstractClassifier classifier, Instances unknownInstances) throws Exception {
        // create copy
        Instances labeled = new Instances(unknownInstances);
        // label instances
        for (int i = 0; i < unknownInstances.numInstances(); i++) {
            double clsLabel = classifier.classifyInstance(unknownInstances.instance(i));
            labeled.instance(i).setClassValue(clsLabel);
        }
        System.out.println("\nNew, labeled = \n" + labeled);
    }

    /**
     * This function is used to load a AbstractClassifier from a file and return it, this file name is hard coded and
     * should not be changed. This wrapper is designed to work with this model only, other models need different inputs
     * and will most probably not work with the rest of the code.
     * @return AbstractClassifier object, the loaded classifier, this can be used to classify new instances.
     */
    private AbstractClassifier loadClassifier() throws Exception {
        // deserialize model
        final String wekaFile = "./src/main/resources/logistic_breast_cancer_model.model";
        return (AbstractClassifier) weka.core.SerializationHelper.read(wekaFile);
    }

    /**
     * Loads data from a file using fileIO and returns this data as an Instances object.
     * @param datafile: String value containing the path to- or name of the file containing the data.
     * @return Instances object containing data as new instances.
     * @throws IOException This function can throw an IOException if the file can't be loaded in properly.
     */
    private Instances loadFile(String datafile) throws IOException {
        try {
            DataSource source = new DataSource(datafile);
            Instances data = source.getDataSet();
            // setting class attribute if the data format does not provide this information
            // For example, the XRFF format saves the class attribute information as well
            if (data.classIndex() == -1)
                data.setClassIndex(data.numAttributes() - 1);
            return data;
        } catch (Exception e) {
            throw new IOException("could not read from file");
        }
    }
}