class SegmentTree_Min {

    static int DEFAULT_VALUE = Integer.MAX_VALUE;

    class Node {
        int min = DEFAULT_VALUE;
        Node lc, rc;
    }

    int LOWERBOUND;
    int UPPERBOUND;
    Node root;

    public SegmentTree_Min(int LOWERBOUND, int UPPERBOUND) {
        this.LOWERBOUND = LOWERBOUND;
        this.UPPERBOUND = UPPERBOUND;
        this.root = new Node();
    }

    public SegmentTree_Min(int[] arr) {
        this.LOWERBOUND = 0;
        this.UPPERBOUND = arr.length - 1;
        this.root = new Node();
        build(arr, root, LOWERBOUND, UPPERBOUND);
    }

    public void set(int idx, int val) {
        set(idx, val, root, LOWERBOUND, UPPERBOUND);
    }

    public void add(int idx, int val) {
        add(idx, val, root, LOWERBOUND, UPPERBOUND);
    }

    public int get(int idx) {
        return get(idx, root, LOWERBOUND, UPPERBOUND);
    }

    public int min(int l, int r) {
        l = Math.max(l, LOWERBOUND);
        r = Math.min(r, UPPERBOUND);
        if (l > r)  return DEFAULT_VALUE;
        if (l == r) return get(l, root, LOWERBOUND, UPPERBOUND);
        return min(l, r, root, LOWERBOUND, UPPERBOUND);
    }

    // Implementations below

    void set(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.min = val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        if (idx <= c)   set(idx, val, node.lc, s, c);
        else            set(idx, val, node.rc, c + 1, t);
        node.min = Math.min(node.lc.min, node.rc.min);
    }

    void add(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.min += val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        if (idx <= c)   add(idx, val, node.lc, s, c);
        else            add(idx, val, node.rc, c + 1, t);
        node.min = Math.min(node.lc.min, node.rc.min);
    }

    int get(int idx, Node node, int s, int t) {
        if (s == t) {
            return node.min;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (idx <= c)   return node.lc == null ? DEFAULT_VALUE : get(idx, node.lc, s, c);
        else            return node.rc == null ? DEFAULT_VALUE : get(idx, node.rc, c + 1, t);
    }

    int min(int l, int r, Node node, int s, int t) {
        if (l <= s && t <= r) {
            return node.min;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        int ret = DEFAULT_VALUE;
        if (l <= c) ret = min(l, r, node.lc, s, c);
        if (c < r)  ret = Math.min(ret, min(l, r, node.rc, c + 1, t));
        return ret;
    }

    void build(int[] arr, Node node, int s, int t) {
        if (s == t) {
            node.min = arr[s];
            return;
        }
        int c = (s & t) +((s ^ t) >> 1);
        node.lc = new Node();
        node.rc = new Node();
        build(arr, node.lc, s, c);
        build(arr, node.rc, c + 1, t);
        node.min = Math.min(node.lc.min, node.rc.min);
    }
}

