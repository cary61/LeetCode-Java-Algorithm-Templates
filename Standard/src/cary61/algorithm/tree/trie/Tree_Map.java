package cary61.algorithm.tree.trie;

import java.util.*;

/**
 * A Trie for strings that might contain any letter, without limit.
 * Implemented by HashMap.
 *
 * @author cary61
 */
class Trie_Map {

    /**
     * The node of tree structure
     */
    class Node {

        /**
         * Represents if there is a way to down for specific letter, and points to it.
         * Each "next.contains(letter)" represents a condition of one letter.
         */
        Map<Character,Node> next = new HashMap<Character,Node>();

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

    Trie_Map() {
        root = new Node();
    }

    /**
     * Insert a new word to trie.
     */
    void insert(String word) {
        int len = word.length();
        Node p = root;
        for (int i = 0; i < len; ++i) {
            char ch = word.charAt(i);
            if (p.next.get(ch) == null) {
                p.next.put(ch, new Node());
            }
            p = p.next.get(ch);
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
            char ch = word.charAt(i);
            if (p.next.get(ch) == null) {
                return false;
            }
            p = p.next.get(ch);
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
            char ch = word.charAt(i);
            if (p.next.get(ch) == null) {
                return false;
            }
            p = p.next.get(ch);
        }
        return p.end;
    }
}
