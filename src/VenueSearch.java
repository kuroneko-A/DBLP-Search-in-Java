/**
 *The VenueSearch class represents a search operation for venues on DBLP (Digital Bibliography & Library Project).
 *It extends the DBLPSearch abstract class and implements the execute() and results() methods to perform a venue search.
 */
import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.SearchResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Constructs a new VenueSearch object with the specified query, apiUrl, and cache directory path.
 * @param query the search query string to be executed.
 * @param apiUrl the URL of the DBLP API endpoint to be used for the search.
 * @param cacheDir the directory path to store the cache files of search results.
 */
public class VenueSearch extends DBLPSearch {
    public VenueSearch(String query, String apiUrl, String cacheDir) {
        super(query, apiUrl, cacheDir);
    }
    
    /**
     * Executes a search for venues with the specified query and cache directory path, and returns a list of search results.
     * @param query the search query string to be executed.
     * @param cacheDir the directory path to store the cache files of search results.
     * @return a list of search results.
     */
    @Override
    public List<SearchResult> execute(String query, String cacheDir) {
        this.query = query;
        this.cacheDir = cacheDir;
        Document doc = getDocument();
        processResults(doc);
    
        return new ArrayList<>();
    }
    
    /**
     * Processes the results of a venue search by extracting and printing the names of the venues from the XML Document object.
     * @param doc the XML Document object representing the results of a venue search.
     */
    protected void processResults(Document doc) {
        // Reference: https://studres.cs.st-andrews.ac.uk/CS1003/Lectures/W03-Examples/W03-5-XMLStreaming/DomExample.java
        // Reference: https://studres.cs.st-andrews.ac.uk/CS1003/Lectures/W03-Examples/W03-3/XMLRev.java

        NodeList venues = doc.getElementsByTagName("venue");
        for (int i = 0; i < venues.getLength(); i++) {
            Element venue = (Element) venues.item(i);
            System.out.println(venue.getTextContent().trim());
        }
    }
}
