import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.File;
import java.util.Arrays;

/**
 * This class is responsible for processing text files using Apache Spark.
 * It reads text files from a specified directory and processes them into a JavaRDD of strings.
 */
public class TextFileProcessor {

    private final JavaSparkContext sparkContext;

    /**
     * Initializes a new instance of the TextFileProcessor class with the specified JavaSparkContext.
     *
     * @param sparkContext The JavaSparkContext instance used for processing the text files.
     */
    public TextFileProcessor(JavaSparkContext sparkContext) {
        this.sparkContext = sparkContext;
    }

    /**
     * Reads text files from the specified directory and processes them into a JavaRDD of strings.
     *
     * @param dataDirPath The path to the directory containing the text files.
     * @return A JavaRDD containing the strings from the text files.
     */
    public JavaRDD<String> readTextFiles(String dataDirPath) {
        //Reference: https://studres.cs.st-andrews.ac.uk/CS1003/Lectures/W09-Examples/SparkText.java
        JavaRDD<String> textWords = sparkContext.emptyRDD();
  
        JavaRDD<String> distFile = sparkContext.textFile(dataDirPath);
        textWords = distFile.flatMap(line -> Arrays.asList(line.split("[ \t\n\r]")).iterator());

        return textWords;
    }
}

