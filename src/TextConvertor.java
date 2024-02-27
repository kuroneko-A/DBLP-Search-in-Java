/**
 * TextConvertor is a utility class that provides text processing and cleaning functionalities. The
 * primary purpose of this class is to convert raw text into a standardized format, such as
 * lowercasing characters and removing non-alphanumeric characters.
 */
public class TextConvertor {
    
    /**
     * Cleans the input text by removing non-alphanumeric characters and converting all characters to lowercase.
     * This method helps to standardize the text format, which is useful for subsequent text processing tasks, such as searching and similarity calculations.
     *
     * @param text The raw input text to clean.
     * @return A cleaned and standardized version of the input text.
     */
    public static String cleanText(String text) {
        text = text.replaceAll("[^a-zA-Z0-9]", " ");
        text = text.toLowerCase();
        return text;
    }
}
