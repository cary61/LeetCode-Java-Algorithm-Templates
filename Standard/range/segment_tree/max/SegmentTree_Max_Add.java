/**
 * A SegmentTree, maintains the max value of range.
 * Capital of updating new value, adding value, multiplying value to single points, and adding value to range, getting the max value of any query range.
 * Implemented by Node.
 * The public methods are advised to use.
 *
 * @author cary61
 */
class SegmentTree_Max_Add {

    /**
     * The default value of the unspecified value
     */
    static final int DEFAULT_VALUE = Integer.MIN_VALUE;

    /**
     * The node of tree structure.
     */
    class Node {

        /**
         * The max value of the range that this node represents.
         */
        int max = DEFAULT_VALUE;

        /**
         * The left child and right child of this node.
         */
        Node lc, rc;

        /**
         * The value that should be added to this node.
         */
        int lazyAdd;
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
    public SegmentTree_Max_Add() {
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
    public SegmentTree_Max_Add(int LOWERBOUND, int UPPERBOUND) {
        this.LOWERBOUND = LOWERBOUND;
        this.UPPERBOUND = UPPERBOUND;
        this.root = new Node();
    }

    /**
     * Instantiate a SegmentTree that maintains an array.
     *
     * @param arr The array that SegmentTree maintains
     */
    public SegmentTree_Max_Add(int[] arr) {
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
     * Add value to every single point in range
     * 
     * @param l the lower-bound of the range
     * @param r the upper-bound of the range
     * @param val the new value
     */
    public void add(int l, int r, int val) {
        add(l, r, val, root, LOWERBOUND, UPPERBOUND);
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
     * Get the max value of range [l, r].
     *
     * @param l the lower-bound of query range
     * @param r the upper-bound of query range
     * @return the sum of query range [l, r]
     */
    public int max(int l, int r) {
        return max(l, r, root, LOWERBOUND, UPPERBOUND);
    }

    

    // Internal Implementations



    void set(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.max = val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (idx <= c)   set(idx, val, node.lc, s, c);
        else            set(idx, val, node.rc, c + 1, t);
        node.max = Math.max(node.lc.max, node.rc.max);
    }

    void add(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.max += val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (idx <= c)   add(idx, val, node.lc, s, c);
        else            add(idx, val, node.rc, c + 1, t);
        node.max = Math.max(node.lc.max, node.rc.max);
    }

    void multiply(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.max *= val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (idx <= c)   multiply(idx, val, node.lc, s, c);
        else            multiply(idx, val, node.rc, c + 1, t);
        node.max = Math.max(node.lc.max, node.rc.max);
    }

    void add(int l, int r, int val, Node node, int s, int t) {
        if (l <= s && t <= r) {
            node.max += val;
            if (s != t) {
                node.lazyAdd += val;
            }
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (l <= c)     add(l, r, val, node.lc, s, c);
        if (c < r)      add(l, r, val, node.rc, c + 1, t);
        node.max = Math.max(node.lc.max, node.rc.max);
    }

    int get(int idx, Node node, int s, int t) {
        if (s == t) {
            return node.max;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
        if (idx <= c)   return get(idx, node.lc, s, c);
        else            return get(idx, node.rc, c + 1, t);
    }

    int max(int l, int r, Node node, int s, int t) {
        if (l <= s && t <= r) {
            return node.max;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        this.pushDown(node, s, t, c);
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

    void pushDown(Node node, int s, int t, int c) {
        if (node.lazyAdd != 0) {
            node.lc.max += node.lazyAdd;
            node.lc.lazyAdd += node.lazyAdd;

            node.rc.max += node.lazyAdd;
            node.rc.lazyAdd += node.lazyAdd;

            node.lazyAdd = 0;
        }
    }
}

