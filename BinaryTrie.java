import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class BinaryTrie implements Serializable {
    //@Source: Princeton Algo's Huffmann.java was instrumental
    //in building this class

    private Node trie;
    private Map<Character, BitSequence> lookupTable = new HashMap<>();


    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        // build trie associated with frequency table
        trie = buildTrie(frequencyTable);

        // Initialize the lookup table we will need later to return the inverse of
        // the coding trie.
        lookupTable = new HashMap<>();
    }

    private Node buildTrie(Map<Character, Integer> frequencyTable) {
        // Build our Trie from a given frequency table

        // create priority queue with singleton trees
        PriorityQueue<Node> queue = new PriorityQueue<>();

        // Iterate thru all the keys (characters) in frequency table to initialize
        // A PQ with trees that are just 1 item.
        for (char i : frequencyTable.keySet()) {
            if (frequencyTable.get(i) > 0) {
                queue.add(new Node(i, frequencyTable.get(i), null, null));
            }
        }
        // Merge the two smallest trees (the first two delMins of the PQ), repeat until
        // we are done
        while (queue.size() > 1) {
            Node left = queue.remove();
            Node right = queue.remove();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            queue.add(parent);
        }
        return queue.remove();
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        // Node we use to keep track of where we're at in the Trie
        Node currTrieNode = trie;

        // Begin to iterate through the whole bit sequence
        for (int i = 0; i < querySequence.length(); i++) {
            if (currTrieNode.isLeaf()) {
                // If we hit a leaf, return a Match object for the match. Else keep going thru seq.
                return new Match(querySequence.firstNBits(i), currTrieNode.c);
            } else {
                // In the if condition, we get bit at the current index i we are in in sequence.
                // recall 0 means left, 1 means right. So go the direction that bit tells us to.
                if (querySequence.bitAt(i) == 0) {
                    currTrieNode = currTrieNode.left;
                } else {
                    currTrieNode = currTrieNode.right;
                }
            }
        }
        return new Match(querySequence, currTrieNode.c);
    }

    public Map<Character, BitSequence> buildLookupTable() {
        traverse(trie, "", 0, true);
        return lookupTable;
    }

    private void traverse(Node root, String bitString, int leftOrRight, boolean initialized) {
        // Need an initialized category since the root of a tree has no 0 or 1
        if (!initialized) {
            bitString += String.valueOf(leftOrRight);
        }
        // If we hit a leaf, add the BitSequence
        if (root.isLeaf()) {
            lookupTable.put(root.c, new BitSequence(bitString));
        // Otherwise, traverse all the left and right paths, keeping track of each path
        } else {
            traverse(root.left, bitString, 0, false);
            traverse(root.right, bitString, 1, false);
        }
    }

    private static class Node implements Serializable, Comparable<Node> {
        // Standard idea of a Node. Similar to a Tree, but works for Tries too.
        private char c;
        private int freq;
        private Node left;
        private Node right;
//        private int leftOrRight;

        Node(char c, int freq, Node left, Node right) {
            this.c = c;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public Node[] children() {
            Node[] children = {this.left, this.right};
            return children;
        }

        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }
}
