import org.xml.sax.SAXException;
//import sun.text.normalizer.Trie;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /**
     * Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc.
     */
    private Map<Long, Node> myNodes = new HashMap<>();
    private Map<Long, ArrayList<Long>> myAdjNodes = new HashMap<>();
    private Tries trieForLocation = new Tries();
    private int numNodes = 0;

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     *
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
//        System.out.println("Hi");
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     *
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     * Remove nodes with no connections from the graph.
     * While this does not guarantee that any two nodes in the remaining graph are connected,
     * we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        ArrayList<Long> ids = new ArrayList<Long>();
        ids.addAll(myNodes.keySet());
        for (long id : ids) {
            if (myAdjNodes.get(id).isEmpty()) {
                myNodes.remove(id);
            }
        }

    }

    public Node getNodes(long id) {
        return myNodes.get(id);
    }

    public boolean hasNodes(long id) {
        return myNodes.containsKey(id);
    }

    public void addNode(Node node) {
        myNodes.put(node.id, node);
        myAdjNodes.put(node.id, new ArrayList<Long>());
        numNodes += 1;
    }

    public int numNodes() {
        return numNodes;
    }


    /**
     * Returns an iterable of all vertex IDs in the graph.
     *
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
//        ArrayList<Long> allVertices = new ArrayList<>();
//        allVertices.addAll(myNodes.keySet());
        return myNodes.keySet();
    }

    public void addEdge(long v, long w) {
        myAdjNodes.get(v).add(w);
        myAdjNodes.get(w).add(v);
    }

    /**
     * Returns ids of all vertices adjacent to v.
     *
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        return myAdjNodes.get(v);
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     *
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     *
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     *
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        double minDis = Double.MAX_VALUE;
        long closestNode = 0;
        for (long id : this.vertices()) {
            double dis = distance(lon, lat, lon(id), lat(id));
//            System.out.println(dis);
            if (dis < minDis) {
                closestNode = id;
                minDis = dis;
            }
        }
        return closestNode;
    }

    /**
     * Gets the longitude of a vertex.
     *
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return getNodes(v).lon;
    }

    /**
     * Gets the latitude of a vertex.
     *
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return getNodes(v).lat;
    }

    static class Node {
        long id;
        double lat;
        double lon;
        Map<String, String> extraInfo;

        Node(long id, double lat, double lon) {
            this.id = id;
            this.lat = lat;
            this.lon = lon;
            this.extraInfo = new HashMap<>();
        }

        public void putLocInTrie (String loc) {
            String cleanedLoc = cleanString(loc);
            Tries trieForLocation = new Tries();
            trieForLocation.put(cleanedLoc, this.id);
        }
    }
//    static class Way {
//        long id;
//        Map<String, String> extraInfo;
//
//        Way(long id) {
//            this.id = id;
//            this.extraInfo = new HashMap<>();
//        }
//    }

    void addWay(ArrayList<Long> thisWay) {
        for (int i = 0; i < thisWay.size() - 1; i++) {
            addEdge(thisWay.get(i), thisWay.get(i + 1));
        }


    }

    public List<String> getLocationsByPrefix(String prefix) {
        String cleanedPre = cleanString(prefix);
        return trieForLocation.keysWithPrefix(cleanedPre);
    }

    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> r = new ArrayList<>();
        List<Long> nodesID = trieForLocation.getNodeByLoc(locationName);
        for (long id : nodesID) {
            System.out.println(id);
            Map<String, Object> n = new HashMap<>();
            n.put("lat", lat(id));
            n.put("lon", lon(id));
            n.put("name", locationName);
            n.put("id", id);
            r.add(n);
        }
        return r;

    }

//    public void putInTrie(String location, long id) {
//        trieForLocation.put(location, id);
//    }


//        addNode

//
//        addEdge
//
//        addWay
//
//        removeNode (goes for method clean)


}

