class SegmentTree_Sum {

    static int DEFAULT_VALUE = 0;

    class Node {
        int sum = DEFAULT_VALUE;
        Node lc, rc;
    }

    int LOWERBOUND;
    int UPPERBOUND;
    Node root;

    public SegmentTree_Sum(int LOWERBOUND, int UPPERBOUND) {
        this.LOWERBOUND = LOWERBOUND;
        this.UPPERBOUND = UPPERBOUND;
        this.root = new Node();
    }

    public SegmentTree_Sum(int[] arr) {
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

    public int sum(int l, int r) {
        if (l > r)  return 0;
        if (l == r) return get(l, root, LOWERBOUND, UPPERBOUND);
        return sum(l, r, root, LOWERBOUND, UPPERBOUND);
    }

    // Implementations below

    void update(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.sum = val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        if (s <= c)     update(idx, val, node.lc, s, c);
        else            update(idx, val, node.rc, c + 1, t);
        node.sum = node.lc.sum + node.rc.sum;
    }

    void add(int idx, int val, Node node, int s, int t) {
        node.sum += val;
        if (s == t) {
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        if (idx <= c)   add(idx, val, node.lc, s, c);
        else            add(idx, val, node.rc, c + 1, t);
    }

    int get(int idx, Node node, int s, int t) {
        if (s == t) {
            return node.sum;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (idx <= c)   return node.lc == null ? DEFAULT_VALUE : get(idx, node.lc, s, c);
        else            return node.rc == null ? DEFAULT_VALUE : get(idx, node.rc, c + 1, t);
    }

    int sum(int l, int r, Node node, int s, int t) {
        if (l <= s && t <= r) {
            return node.sum;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        int ret = 0;
        if (l <= c) ret = sum(l, r, node.lc, s, c);
        if (c < r)  ret += sum(l, r, node.rc, c + 1, t);
        return ret;
    }

    void build(int[] arr, Node node, int s, int t) {
        if (s == t) {
            node.sum = arr[s];
            return;
        }
        int c = (s & t) +((s ^ t) >> 1);
        node.lc = new Node();
        node.rc = new Node();
        build(arr, node.lc, s, c);
        build(arr, node.rc, c + 1, t);
        node.sum = node.lc.sum + node.rc.sum;
    }
}
