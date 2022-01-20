import weka.classifiers.AbstractClassifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;


import java.io.IOException;


public class WekaWrapper {
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

    private AbstractClassifier loadClassifier() throws Exception {
        // deserialize model
        String wekaFile = "./src/main/resources/logistic_breast_cancer_model.model";
        return (AbstractClassifier) weka.core.SerializationHelper.read(wekaFile);
    }

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