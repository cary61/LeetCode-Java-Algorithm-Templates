/**
 * A SegmentTree for maintain the min value of range.
 * Capital of updating new value, adding value to single points, and getting the min value of any query range.
 * Implemented by Node.
 * The public methods are advised to use.
 *
 * @author cary61
 */
class SegmentTree_Min {

    /**
     * The default value of the unspecified value
     */
    static int DEFAULT_VALUE = Integer.MAX_VALUE;

    /**
     * The node of tree structure.
     */
    class Node {

        /**
         * The min value of the range that this node represents.
         */
        int min = DEFAULT_VALUE;

        /**
         * The left child and right child of this node.
         */
        Node lc, rc;
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
     * Instantiate a SegmentTree that maintains a range, with the lower-bound and upper-bound of it.
     * The value of the points that have not been specified is DEFAULT_VALUE.
     *
     * @param LOWERBOUND the lower-bound of range
     * @param UPPERBOUND the upper-bound of range
     */
    public SegmentTree_Min(int LOWERBOUND, int UPPERBOUND) {
        this.LOWERBOUND = LOWERBOUND;
        this.UPPERBOUND = UPPERBOUND;
        this.root = new Node();
    }

    /**
     * Instantiate a SegmentTree that maintains an array.
     *
     * @param arr The array that SegmentTree maintains
     */
    public SegmentTree_Min(int[] arr) {
        this.LOWERBOUND = 0;
        this.UPPERBOUND = arr.length - 1;
        this.root = new Node();
        build(arr, root, LOWERBOUND, UPPERBOUND);
    }

    /**
     * Update a single point with a new value.
     *
     * @param idx the index of the single point to update
     * @param val the new value of the single point
     */
    public void update(int idx, int val) {
        update(idx, val, root, LOWERBOUND, UPPERBOUND);
    }

    /**
     * Add a value to a single point
     *
     * @param idx the index of the single point
     * @param val the value that added to the single point
     */
    public void add(int idx, int val) {
        add(idx, val, root, LOWERBOUND, UPPERBOUND);
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
     * Get the min value of range [l, r].
     *
     * @param l the lower-bound of query range
     * @param r the upper-bound of query range
     * @return the sum of query range [l, r]
     */
    public int min(int l, int r) {
        if (l > r)  return DEFAULT_VALUE;
        if (l == r) return get(l, root, LOWERBOUND, UPPERBOUND);
        return min(l, r, root, LOWERBOUND, UPPERBOUND);
    }

    // Implementations below

    void update(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.min = val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        if (s <= c)     update(idx, val, node.lc, s, c);
        else            update(idx, val, node.rc, c + 1, t);
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


