/**
 *The AuthorSearch class represents a search operation for authors on DBLP (Digital Bibliography & Library Project).
 *It extends the DBLPSearch abstract class and implements the execute() and processResults() methods to perform an author search.
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.directory.SearchResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Constructs a new AuthorSearch object with the specified query, apiUrl, and cache directory path.
 * @param query the search query string to be executed.
 * @param apiUrl the URL of the DBLP API endpoint to be used for the search.
 * @param cacheDir the directory path to store the cache files of search results.
 */
public class AuthorSearch extends DBLPSearch {
    public AuthorSearch(String query, String apiUrl, String cacheDir) {
        super(query, apiUrl, cacheDir);
    }
    
    /**
     * Executes a search for authors with the specified query and cache directory path, and returns a list of search results.
     * @param query the search query string to be executed.
     * @param cacheDir the directory path to store the cache files of search results.
     * @return a list of search results.
     **/
    @Override
    public List<SearchResult> execute(String query, String cacheDir) {
        this.query = query;
        this.cacheDir = cacheDir;
        Document doc = getDocument();
        processResults(doc);
    
        return new ArrayList<>();
    }
    
    /**
     * Processes the results of an author search by extracting and printing the name, number of publications, and number of co-authors of the authors from the XML Document object.
     * @param doc the XML Document object representing the results of an author search.
     */
    protected void processResults(Document doc) {

        NodeList hits = doc.getElementsByTagName("hit");
        for (int i = 0; i < hits.getLength(); i++) {
            Element hit = (Element) hits.item(i);
            Element info = (Element) hit.getElementsByTagName("info").item(0);
            String authorName = info.getElementsByTagName("author").item(0).getTextContent().trim();
            String authorUrl = info.getElementsByTagName("url").item(0).getTextContent().trim();
            int numPublications = getNumPublications(authorUrl, cacheDir);
            //System.out.println(numPublications);
            int numCoauthors = getNumCoauthors(authorUrl, cacheDir);
            System.out.println(authorName + " - " + numPublications + " publications with " + numCoauthors + " co-authors.");
        }
    }
     
    /**
     * Returns the number of publications of the author from the specified URL and cache directory path.
     * @param url the URL of the author to retrieve the number of publications from.
     * @param cacheDir the directory path to store the cache files of search results.
     * @return the number of publications of the author.
     */
    public int getNumPublications(String url, String cacheDir) {
        // Reference: Notice the lack of information about number of publications and the number of co-authors in the XML output. This data is available through the URLs listed in the XML response, inside the info elements. By adding an .xml extension to these URLs you can retrieve the data in a machine-readable format. Try visiting these URLs in your browser to get a better idea of what the file contents look like. 
        String apiUrl = url + ".xml";
        Document doc = XmlParser.getDocument(apiUrl, cacheDir);
        NodeList nodes = doc.getElementsByTagName("r");
        int count = nodes.getLength();
        // debugging: for (int i = 0; i < nodes.getLength(); i++) {
        //     Element node = (Element) nodes.item(i);
        //     if (node.getAttribute("t").equals("article") || node.getAttribute("t").equals("inproceedings")) {
        //         count++;
        //         System.out.println(count);
        //     }
        // }
        return count;
    }
    
    /**
     * Returns the number of co-authors of the author from the specified URL and cache directory path.
     * @param url the URL of the author to retrieve the number of co-authors from.
     * @param cacheDir the directory path to store the cache files of search results.
     * @return the number of co-authors of the author.
     */
    private int getNumCoauthors(String url, String cacheDir) {
        String apiUrl = url + ".xml";
        Document doc = XmlParser.getDocument(apiUrl, cacheDir);
        NodeList nodes = doc.getElementsByTagName("co");
        Set<String> coauthors = new HashSet<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            String coauthor = nodes.item(i).getTextContent().trim();
            if (!coauthor.isEmpty()) {
                coauthors.add(coauthor);
            }
        }
        return coauthors.size();
    }
}
