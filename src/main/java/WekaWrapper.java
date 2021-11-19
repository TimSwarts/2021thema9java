import weka.classifiers.AbstractClassifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WekaWrapper {
    public static void main(String[] args) throws InterruptedException, IOException {
        WekaWrapper runWeka = new WekaWrapper();
        ApacheCli apacheCli = new ApacheCli(args);
        String inputFile = apacheCli.fileName;
        if (inputFile == null){
            System.exit(0);
        }
        runWeka.start(inputFile);
    }

    private static void modifyFileR(String filename) throws InterruptedException, IOException {
        String cmd = "Rscript ./src/main/resources/r_script_for_java.R " + filename;
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(cmd);
        pr.waitFor();
        BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line;
        while ((line=buf.readLine())!= null) {
            System.out.println(line);
        }
    }

    private void start(String inputFile) {
        try {
            AbstractClassifier fromFile = loadClassifier();
            Instances unknownInstances = loadFile(inputFile);
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