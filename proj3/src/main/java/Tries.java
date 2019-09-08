import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/* @Source <https://algs4.cs.princeton.edu/52trie/TrieST.java.html> */

public class Tries {
    private TrieNode root;
    private int x;


    private class TrieNode {
        private boolean isWord;
        Map<Character, TrieNode> children;
        private ArrayList<Long> nodeId;
        private long id;

        public TrieNode() {
            root = new TrieNode();
            this.isWord = false;
            this.nodeId = new ArrayList<Long>();

        }

    }

    public void put(String key, long id) {
        if (key.isEmpty()) throw new IllegalArgumentException("first argument to put() is null");
        else put(root, key, id, 0);
    }

    private void put(TrieNode curr, String key, long id, int d) {
        if (d == key.length()) {
            curr.isWord = true;
            curr.nodeId.add(id);
            return;
        }
        char ch = key.charAt(d);
        TrieNode node = curr.children.get(ch);

        if (node == null) {
            node = new TrieNode();
            curr.children.put(ch, node);
        }
        put(node, key, id, d + 1);
    }


    private TrieNode get(TrieNode x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.children.get(c), key, d + 1);
    }

    public ArrayList<String> keysWithPrefix(String prefix) {
        ArrayList<String> results = new ArrayList<String>();
        TrieNode x = get(root, prefix, 0);
        collect(x, prefix, results);
        return results;
    }

    private void collect(TrieNode n, String prefix, ArrayList<String> results) {
        if (n == null) return;
        if (n.isWord) results.add(prefix.toString());
        for (char c = 0; c < 256; c++) {
            collect(n.children.get(c), prefix, results);
        }

    }

    public ArrayList<Long> getNodeByLoc(String locName) {
        TrieNode n = root;
        for (int i = 0; i < locName.length(); i++) {
            char c = locName.charAt(i);
            n = n.children.get(c);
        }
        if (n.isWord) {
            return n.nodeId;
        } else {
            return null;
        }
    }

}
