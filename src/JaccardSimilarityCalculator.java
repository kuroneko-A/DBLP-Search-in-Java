import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is responsible for calculating Jaccard similarity between two strings.
 * It uses character bigrams to calculate the similarity.
 */
public class JaccardSimilarityCalculator {

    /**
     * Calculates the Jaccard similarity between two input strings.
     *
     * @param word1 The first input string.
     * @param word2 The second input string.
     * @return A BigDecimal value representing the Jaccard similarity between the two input strings.
     */
    public BigDecimal calculate(String word1, String word2) {

        Set<String> bigrams1 = calculateCharacterBigrams(word1);
        //LinkedList<String> bigrams1 = calculateCharacterBigramsByList(word1);
        Set<String> bigrams2 = calculateCharacterBigrams(word2);
        //LinkedList<String> bigrams2 = calculateCharacterBigramsByList(word2);

        Set<String> intersection = new HashSet<>(bigrams2);
        intersection.retainAll(bigrams1);
        BigDecimal intersectionSize = new BigDecimal(intersection.size());

        Set<String> union = new HashSet<>(bigrams1);
        union.addAll(bigrams2);
        BigDecimal unionSize = new BigDecimal(union.size());
      
        BigDecimal jaccardSimilarity = intersectionSize.divide(unionSize, new MathContext(100));
        // if (word2.trim().equals("the christmas tree carefully helen")) {
        //     System.out.println(intersection.size());
        //     System.out.println(union.size());
        //     System.out.println(jaccardSimilarity);

        // }

        return jaccardSimilarity;
    }

    /**
     * Calculates the character bigrams for a given input string.
     *
     * @param word The input string for which to calculate the character bigrams.
     * @return A Set of character bigrams for the input string.
     */
    public Set<String> calculateCharacterBigrams(String word) {
        Set<String> characterBigrams = new HashSet<>();
        for (int i = 0; i < word.length() - 1; i++) {
            //Reference: https://www.geeksforgeeks.org/character-iswhitespace-method-in-java-with-examples/
            char currentChar = word.charAt(i);
            char nextChar = word.charAt(i + 1);
            if (Character.isWhitespace(currentChar) || Character.isWhitespace(nextChar)) {
                continue;
            }
            characterBigrams.add(word.substring(i, i + 2));
        }
        return characterBigrams;
    }
}
