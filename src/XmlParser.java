/**
 *The XmlParser class is a utility class that provides functionality to parse and save XML documents.
 *It also provides an in-memory cache to store XML documents for a given URL to reduce the number of API calls.
 */
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.*;

public class XmlParser {
    // A Map used to store the cached XML documents where the key is the URL and the value is the Document object.
    private static Map<String, Document> cache = new HashMap<>();

    /**
     * Retrieves an XML document from the specified URL and returns it as a Document object. 
     * The method also saves the document to the cache directory and adds it to the cache for future use.
     * If the document is already in the cache, it is retrieved from the cache instead of making a new API call.
     * @param url the URL of the XML document to be retrieved.
     * @param cacheDir the directory path to store the cache files of retrieved XML documents.
     * @return a Document object representing the XML response from the specified URL, or null if the document cannot be retrieved.
     */
    public static Document getDocument(String url, String cacheDir) {
        if (cache.containsKey(url)) {
            //System.out.println("Using cached response for " + url); (used for clear testing)
            return cache.get(url);
        }
        try {
            // Reference: https://studres.cs.st-andrews.ac.uk/CS1003/Lectures/W03-Examples/W03-2-SimpleXMLWriter/SimpleXMLWriter.java
            // Reference: https://stackoverflow.com/questions/38904352/parsing-xml-from-url-in-java
    
            //System.out.println("Making API call to " + url); (used for clear testing)
            URL apiUrl = new URL(url);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(apiUrl.openConnection().getInputStream());
            cache.put(url, doc);
            saveDocument(doc, cacheDir, url);
            return doc;

        } catch (MalformedURLException e) {
            System.err.println("Malformed URL: " + url);
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            System.err.println("Error parsing XML document");
            e.printStackTrace();
        } catch (SAXException e) {
            System.err.println("Error parsing XML document");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading from URL: " + url);
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Saves the specified XML Document object to a file in the specified cache directory with the URL as the filename.
     * @param doc the XML Document object to be saved to the cache directory.
     * @param cacheDir the directory path to store the cache files of retrieved XML documents.
     * @param url the URL of the document to be used as the cache filename.
     */
    private static void saveDocument(Document doc, String cacheDir, String url) {
        try {
            //// Reference: https://studres.cs.st-andrews.ac.uk/CS1003/Lectures/W03-Examples/W03-2-SimpleXMLWriter/SimpleXMLWriter.java
            String filename = URLEncoder.encode(url, StandardCharsets.UTF_8).replaceAll("\\+", "%20") + " .xml";
            File file = new File(cacheDir, filename);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            writer.close();
        } catch (Exception e) {
            System.err.println("Error saving document to cache");
            e.printStackTrace();
        }
    }
}
