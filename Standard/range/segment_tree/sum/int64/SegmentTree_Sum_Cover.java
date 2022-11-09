/**
 * A SegmentTree, maintains the sum of range.
 * Capital of updating new value, adding value, multiplying value to single points, cover range with new value, and getting the sum of any query range.
 * Implemented by Node.
 * The public methods are advised to use.
 * The sum of any range should be guaranteed in range of int64.
 *
 * @author cary61
 */
class SegmentTree_Sum_Cover {

    /**
     * The default value of the unspecified value
     */
    static final long DEFAULT_VALUE = 0;

    /**
     * The node of tree structure.
     */
    class Node {

        /**
         * The sum of the range that this node represents.
         */
        long sum = DEFAULT_VALUE;

        /**
         * The left child and right child of this node.
         */
        Node lc, rc;

        /**
         * The value that should cover this node.
         */
        long lazyCover;

        /**
         * When false, represents the lazyCover should be push down.
         */
        boolean updated = true;
    }

    /**
     * The lower-bound of range.
     */
    int LOWERBOUND;

    /**
     * The upper-bound of range.
     */
    int UPPERBOUND;

    /**
     * The root of tree structure.
     */
    Node root;

    /**
     * Instantiate a SegmentTree that maintains the range [MIN_INT, MAX_INT].
     * The value of the points that have not been specified is DEFAULT_VALUE.
     */
    public SegmentTree_Sum_Cover() {
        this.LOWERBOUND = Integer.MIN_VALUE;
        this.UPPERBOUND = Integer.MAX_VALUE;
        this.root = new Node();
    }

    /**
     * Instantiate a SegmentTree that maintains a range, with the lower-bound and upper-bound of it.
     * The value of the points that have not been specified is DEFAULT_VALUE.
     *
     * @param LOWERBOUND the lower-bound of range
     * @param UPPERBOUND the upper-bound of range
     */
    public SegmentTree_Sum_Cover(int LOWERBOUND, int UPPERBOUND) {
        this.LOWERBOUND = LOWERBOUND;
        this.UPPERBOUND = UPPERBOUND;
        this.root = new Node();
    }

    /**
     * Instantiate a SegmentTree that maintains an array.
     *
     * @param arr The array that SegmentTree maintains
     */
    public SegmentTree_Sum_Cover(int[] arr) {
        this.LOWERBOUND = 0;
        this.UPPERBOUND = arr.length - 1;
        this.root = new Node();
        build(arr, root, LOWERBOUND, UPPERBOUND);
    }

    /**
     * Set a single point with a new value.
     *
     * @param idx the index of the single point to set
     * @param val the new value of the single point
     */
    public void set(int idx, int val) {
        set(idx, val, root, LOWERBOUND, UPPERBOUND);
    }

    /**
     * Add a value to a single point.
     *
     * @param idx the index of the single point
     * @param val the value that added to the single point
     */
    public void add(int idx, int val) {
        add(idx, val, root, LOWERBOUND, UPPERBOUND);
    }

    /**
     * Make a single point multiply a value.
     * 
     * @param idx the index of the single point
     * @param val the value that be multiplied
     */
    public void multiply(int idx, int val) {
        multiply(idx, val, root, LOWERBOUND, UPPERBOUND);
    }

    /**
     * Cover every single point of range with new value.
     * 
     * @param l  the lower-bound of the range
     * @param r the upper-bound of the range
     * @param val the new value
     */
    public void cover(int l, int r, int val) {
        cover(l, r, val, root, LOWERBOUND, UPPERBOUND);
    }

    /**
     * Get the value of a single point.
     *
     * @param idx the index of the single point
     * @return the value of the single point
     */
    public int get(int idx) {
        return get(idx, root, LOWERBOUND, UPPERBOUND);
    }

    /**
     * Get the sum of range [l, r].
     *
     * @param l the lower-bound of query range
     * @param r the upper-bound of query range
     * @return the sum of query range [l, r]
     */
    public long sum(int l, int r) {
        return sum(l, r, root, LOWERBOUND, UPPERBOUND);
    }

    

    // Internal Implementations



    void set(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.sum = val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (idx <= c)   set(idx, val, node.lc, s, c);
        else            set(idx, val, node.rc, c + 1, t);
        node.sum = node.lc.sum + node.rc.sum;
    }

    void add(int idx, int val, Node node, int s, int t) {
        node.sum += val;
        if (s == t) {
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (idx <= c)   add(idx, val, node.lc, s, c);
        else            add(idx, val, node.rc, c + 1, t);
    }

    void multiply(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.sum *= val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (idx <= c)   multiply(idx, val, node.lc, s, c);
        else            multiply(idx, val, node.rc, c + 1, t);
        node.sum = node.lc.sum + node.rc.sum;
    }

    void cover(int l, int r, int val, Node node, int s, int t) {
        if (l <= s && t <= r) {
            node.sum = (t - s + 1) * val;
            if (s != t) {
                node.lazyCover = val;
                node.updated = false;
            }
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (l <= c)         cover(l, r, val, node.lc, s, c);
        if (c < r)     cover(l, r, val, node.rc, c + 1, t);
        node.sum = node.lc.sum + node.rc.sum;
    }

    int get(int idx, Node node, int s, int t) {
        if (s == t) {
            return (int)node.sum;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (idx <= c)   return get(idx, node.lc, s, c);
        else            return get(idx, node.rc, c + 1, t);
    }

    long sum(int l, int r, Node node, int s, int t) {
        if (l <= s && t <= r) {
            return node.sum;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        long ret = 0;
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

    void pushDown(Node node, int s, int t, int c) {
        if (!node.updated) {
            node.lc.sum = (c - s + 1) * node.lazyCover;
            node.lc.lazyCover = node.lazyCover;
            node.lc.updated = false;

            node.rc.sum = (t - c) * node.lazyCover;
            node.rc.lazyCover = node.lazyCover;
            node.rc.updated = false;

            node.updated = true;
        }
    }
}
