/**
 *The Cache class provides a simple in-memory cache for storing XML documents retrieved from URLs.
 *The cache is implemented using a Map where the key is the URL of the document and the value is the Document object.
 */
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    // A Map used to store the cached documents where the key is the URL and the value is the Document object.
    private static Map<String, Document> cache = new HashMap<>();
    
    /**
     * Gets the cached document corresponding to the specified URL.
     * @param url the URL of the document to retrieve from the cache.
     * @return the Document object corresponding to the specified URL, or null if the document is not in the cache.
     */
    public static Document getDocument(String url) {
        return cache.get(url);
    }
    
    /**
     * Adds the specified Document object to the cache with the specified URL as the key.
     * @param url the URL of the document to be used as the cache key.
     * @param doc the Document object to be stored in the cache.
     */
    public static void putDocument(String url, Document doc) {
        cache.put(url, doc);
    }
}
