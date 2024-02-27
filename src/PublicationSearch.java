/**
 *The PublicationSearch class represents a search operation for publications on DBLP (Digital Bibliography & Library Project).
 *It extends the DBLPSearch abstract class and implements the execute() and processResults() methods to perform a publication search.
 */
import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.SearchResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Constructs a new PublicationSearch object with the specified query, apiUrl, and cache directory path.
 * @param query the search query string to be executed.
 * @param apiUrl the URL of the DBLP API endpoint to be used for the search.
 * @param cacheDir the directory path to store the cache files of search results.
 */
public class PublicationSearch extends DBLPSearch {
    public PublicationSearch(String query, String apiUrl, String cacheDir) {
        super(query, apiUrl, cacheDir);
    }
    
    /**
     * Executes a search for publications with the specified query and cache directory path, and returns a list of search results.
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
     * Processes the results of a publication search by extracting and printing the titles and number of authors of the publications from the XML Document object.
     * @param doc the XML Document object representing the results of a publication search.
     */
    protected void processResults(Document doc) {
        // Reference: https://studres.cs.st-andrews.ac.uk/CS1003/Lectures/W03-Examples/W03-5-XMLStreaming/DomExample.java
        // Reference: https://studres.cs.st-andrews.ac.uk/CS1003/Lectures/W03-Examples/W03-3/XMLRev.java

        NodeList hits = doc.getElementsByTagName("hit");
        for (int i = 0; i < hits.getLength(); i++) {
            Element hit = (Element) hits.item(i);
            Element info = (Element) hit.getElementsByTagName("info").item(0);
            String title = info.getElementsByTagName("title").item(0).getTextContent().trim();
            int numAuthors = info.getElementsByTagName("author").getLength();
            System.out.println(title + " (number of authors: " + numAuthors + ")");
        }
    }
}

