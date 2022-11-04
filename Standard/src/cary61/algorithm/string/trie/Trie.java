package cary61.algorithm.string.trie;

/**
 * A Trie for strings that only contain lowercase letters.
 * Implemented simply by array.
 *
 * @author cary61
 */
class Trie {

    /**
     * The node of tree structure
     */
    class Node {

        /**
         * Represents if there is a way to down for specific letter, and points to it.
         * Each next[i] represents a condition of one letter.
         */
        Node[] next = new Node[26];

        /**
         * Represent if the prefix that the way down represents can be an intact word.
         */
        boolean end;
    }

    /**
     * Root of the tree structure.
     * Usually used as starting point of dfs.
     */
    Node root;

    Trie() {
        root = new Node();
    }

    /**
     * Insert a new word to trie.
     */
    void insert(String word) {
        int len = word.length();
        Node p = root;
        for (int i = 0; i < len; ++i) {
            int pos = word.charAt(i) - 'a';
            if (p.next[pos] == null) {
                p.next[pos] = new Node();
            }
            p = p.next[pos];
        }
        p.end = true;
    }

    /**
     * Check if a word is contained as a prefix of word in trie.
     * <p>The whole word is considered to be its prefix, too.</p>
     */
    boolean containsPrefix(String word) {
        int len = word.length();
        Node p = root;
        for (int i = 0; i < len; ++i) {
            int pos = word.charAt(i) - 'a';
            if (p.next[pos] == null) {
                return false;
            }
            p = p.next[pos];
        }
        return true;
    }

    /**
     * Check if a word is contained in trie.
     */
    boolean containsWord(String word) {
        int len = word.length();
        Node p = root;
        for (int i = 0; i < len; ++i) {
            int pos = word.charAt(i) - 'a';
            if (p.next[pos] == null) {
                return false;
            }
            p = p.next[pos];
        }
        return p.end;
    }
}
