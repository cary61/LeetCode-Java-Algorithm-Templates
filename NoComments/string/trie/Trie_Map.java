// import java.util.*;

class Trie_Map {

    class Node {
        java.util.Map<Character,Node> next = new java.util.HashMap<Character,Node>();
        boolean end;
    }

    Node root;

    Trie_Map() {
        root = new Node();
    }

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
