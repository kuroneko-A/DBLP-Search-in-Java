import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TextSearch extends DBLPSearch {

    private final JavaSparkContext sparkContext;
    private final BigDecimal similarityThreshold;
    private final JaccardSimilarityCalculator similarityCalculator;

    public TextSearch(String query, String apiUrl, String cacheDir, JavaSparkContext sparkContext, BigDecimal similarityThreshold) {
        super(query, apiUrl, cacheDir);
        this.sparkContext = sparkContext;
        this.similarityThreshold = similarityThreshold;
        this.similarityCalculator = new JaccardSimilarityCalculator();
    }

    @Override
    public List<SearchResult> execute(String query, String cacheDir) {
        // This method should be overridden to implement the text search logic
        // However, since the original class performs Spark-based text search,
        // you may want to refactor the logic to fit the execute method signature
        // of the DBLPSearch abstract class.
        return new ArrayList<>();
    }

    public JavaRDD<String> search(String searchTerm, JavaRDD<String> textFiles) {
        String cleanedSearchTerm = TextConvertor.cleanText(searchTerm);
        String[] searchTermWords = cleanedSearchTerm.split("[ \t\n\r]+");
        int numWordsInSearchTerm = searchTermWords.length;

        JavaRDD<String> words = textFiles.flatMap(line -> Arrays.asList(TextConvertor.cleanText(line).split("[ \t\n\r]+")).iterator()).filter(word -> !(word.equals("") || word.equals("")));

        List<String> textWords = words.collect();
        List<String> matchingSubsequences = new ArrayList<>();

        for (int i = 0; i <= textWords.size() - numWordsInSearchTerm; i++) {
            StringBuilder subsequence = new StringBuilder();
            for (int j = 0; j < numWordsInSearchTerm; j++) {
                subsequence.append(textWords.get(i + j));
                if (j <= numWordsInSearchTerm - 1) {
                    subsequence.append(" ");
                }
            }

            BigDecimal similarity = similarityCalculator.calculate(cleanedSearchTerm, subsequence.toString());

            if (similarity.compareTo(similarityThreshold) >= 0) {
                matchingSubsequences.add(subsequence.toString().trim());
            }
        }
        return sparkContext.parallelize(matchingSubsequences);
    }

    // Other methods of TextSearch class can go here
}

