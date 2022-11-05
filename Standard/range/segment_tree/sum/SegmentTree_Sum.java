/**
 * A SegmentTree, maintains the sum of range.
 * Capital of updating new value, adding value to single points, and getting the sum of any query range.
 * Implemented by Node.
 * The public methods are advised to use.
 * The sum of any range should be guaranteed in range of int32. If not, use the int64 version.
 *
 * @author cary61
 */
class SegmentTree_Sum {

    /**
     * The default value of the unspecified value
     */
    static int DEFAULT_VALUE = 0;

    /**
     * The node of tree structure.
     */
    class Node {

        /**
         * The sum of the range that this node represents.
         */
        int sum = DEFAULT_VALUE;

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
    public SegmentTree_Sum(int LOWERBOUND, int UPPERBOUND) {
        this.LOWERBOUND = LOWERBOUND;
        this.UPPERBOUND = UPPERBOUND;
        this.root = new Node();
    }

    /**
     * Instantiate a SegmentTree that maintains an array.
     *
     * @param arr The array that SegmentTree maintains
     */
    public SegmentTree_Sum(int[] arr) {
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
     * Get the sum of range [l, r].
     *
     * @param l the lower-bound of query range
     * @param r the upper-bound of query range
     * @return the sum of query range [l, r]
     */
    public int sum(int l, int r) {
        l = Math.max(l, LOWERBOUND);
        r = Math.min(r, UPPERBOUND);
        if (l > r)  return 0;
        if (l == r) return get(l, root, LOWERBOUND, UPPERBOUND);
        return sum(l, r, root, LOWERBOUND, UPPERBOUND);
    }

    // Implementations below

    void set(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.sum = val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
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
