class SegmentTree_Max {

    static int DEFAULT_VALUE = Integer.MIN_VALUE;

    class Node {
        int max = DEFAULT_VALUE;
        Node lc, rc;
    }

    int LOWERBOUND;
    int UPPERBOUND;
    Node root;

    public SegmentTree_Max(int LOWERBOUND, int UPPERBOUND) {
        this.LOWERBOUND = LOWERBOUND;
        this.UPPERBOUND = UPPERBOUND;
        this.root = new Node();
    }

    public SegmentTree_Max(int[] arr) {
        this.LOWERBOUND = 0;
        this.UPPERBOUND = arr.length - 1;
        this.root = new Node();
        build(arr, root, LOWERBOUND, UPPERBOUND);
    }

    public void update(int idx, int val) {
        update(idx, val, root, LOWERBOUND, UPPERBOUND);
    }

    public void add(int idx, int val) {
        add(idx, val, root, LOWERBOUND, UPPERBOUND);
    }

    public int get(int idx) {
        return get(idx, root, LOWERBOUND, UPPERBOUND);
    }

    public int max(int l, int r) {
        if (l > r)  return DEFAULT_VALUE;
        if (l == r) return get(l, root, LOWERBOUND, UPPERBOUND);
        return max(l, r, root, LOWERBOUND, UPPERBOUND);
    }

    // Implementations below

    void update(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.max = val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        if (s <= c)     update(idx, val, node.lc, s, c);
        else            update(idx, val, node.rc, c + 1, t);
        node.max = Math.max(node.lc.max, node.rc.max);
    }

    void add(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.max += val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        if (idx <= c)   add(idx, val, node.lc, s, c);
        else            add(idx, val, node.rc, c + 1, t);
        node.max = Math.max(node.lc.max, node.rc.max);
    }

    int get(int idx, Node node, int s, int t) {
        if (s == t) {
            return node.max;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (idx <= c)   return node.lc == null ? DEFAULT_VALUE : get(idx, node.lc, s, c);
        else            return node.rc == null ? DEFAULT_VALUE : get(idx, node.rc, c + 1, t);
    }

    int max(int l, int r, Node node, int s, int t) {
        if (l <= s && t <= r) {
            return node.max;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        int ret = DEFAULT_VALUE;
        if (l <= c) ret = max(l, r, node.lc, s, c);
        if (c < r)  ret = Math.max(ret, max(l, r, node.rc, c + 1, t));
        return ret;
    }

    void build(int[] arr, Node node, int s, int t) {
        if (s == t) {
            node.max = arr[s];
            return;
        }
        int c = (s & t) +((s ^ t) >> 1);
        node.lc = new Node();
        node.rc = new Node();
        build(arr, node.lc, s, c);
        build(arr, node.rc, c + 1, t);
        node.max = Math.max(node.lc.max, node.rc.max);
    }
}

