package nocomments.trie;

class Trie {

    class Node {
        Node[] next = new Node[26];
        boolean end;
    }

    Node root;

    Trie() {
        root = new Node();
    }

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
