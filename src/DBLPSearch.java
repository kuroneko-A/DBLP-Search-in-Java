/**
 *The DBLPSearch class is an abstract class representing a search operation on DBLP (Digital Bibliography & Library Project).
 *It contains common functionality and fields for all search types.
 */
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.naming.directory.SearchResult;

import org.w3c.dom.Document;

public abstract class DBLPSearch {
    // The search query string.
    protected String query;
    // The URL of the DBLP API to be used for the search.
    protected String apiUrl;
    // The directory path to store the cache files of search results.
    protected String cacheDir;
    /**
     * Constructs a new DBLPSearch object with the specified query, apiUrl, and cache directory path.
     * @param query the search query string to be executed.
     * @param apiUrl the URL of the DBLP API to be used for the search.
     * @param cacheDir the directory path to store the cache files of search results.
     */
    public DBLPSearch(String query, String apiUrl, String cacheDir) {
        this.query = query;
        this.apiUrl = apiUrl;
        this.cacheDir = cacheDir;
    }
    
    /**
     * Executes the search operation and returns a list of search results.
     * @param query the search query string to be executed.
     * @param cacheDir the directory path to store the cache files of search results.
     * @return a list of search results.
     */
    public abstract List<SearchResult> execute(String query, String cacheDir);

    /**
     * Sends a GET request to the specified URL to retrieve an XML document and returns it as a Document object.
     * @return a Document object representing the XML response from the specified URL.
     */
    protected Document getDocument() {
        String url = apiUrl+ URLEncoder.encode(query, StandardCharsets.UTF_8);
        return XmlParser.getDocument(url, cacheDir);
    }
}
