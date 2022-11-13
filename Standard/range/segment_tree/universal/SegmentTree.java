/**
 * A SegmentTree, maintains the specified result of range.
 * Capital of any operations to single points, and getting the result of any query range.
 * Implemented by Node.
 * The public methods are advised to use.
 * The sum of any range should be guaranteed in range of int32.
 *
 * @author cary61
 */
class SegmentTree {

    class Node {

        /**
         * The value of the range that this node represents.
         */
        int value = DEFAULT_VALUE;

        /**
         * The left child and right child of this node.
         */
        Node lc, rc;
    }

    /**
     * The default value of the unspecified value
     */
    int DEFAULT_VALUE;

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
     * The operation of pushing up.
     */
    java.util.function.IntBinaryOperator pushUp;

    /**
     * Instantiate a SegmentTree that maintains a range, with the lower-bound and upper-bound of it.
     * The value of the points that have not been specified is DEFAULT_VALUE.
     *
     * @param LOWERBOUND the lower-bound of range
     * @param UPPERBOUND the upper-bound of range
     * @param DEFAULT_VALUE the default value of the unspecified point
     */
    public SegmentTree(int LOWERBOUND, int UPPERBOUND, int DEFAULT_VALUE, java.util.function.IntBinaryOperator pushUp) {
        this.LOWERBOUND = LOWERBOUND;
        this.UPPERBOUND = UPPERBOUND;
        this.DEFAULT_VALUE = DEFAULT_VALUE;
        this.pushUp = pushUp;
        this.root = new Node();
    }
    
    /**
     * Update a single point to new value.
     * 
     * @param idx the index of single point
     * @param operation the operation
     */
    public void update(int idx, java.util.function.IntUnaryOperator operation) {
        update(idx, operation, root, LOWERBOUND, UPPERBOUND);
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
     * Get the value of a single point.
     *
     * @param idx the index of the single point
     * @return the value of the single point
     */
    public int get(int idx) {
        return get(idx, root, LOWERBOUND, UPPERBOUND);
    }

    /**
     * Get the result of query of range [l, r].
     *
     * @param l the lower-bound of query range
     * @param r the upper-bound of query range
     * @return the sum of query range [l, r]
     */
    public int query(int l, int r) {
        return query(l, r, root, LOWERBOUND, UPPERBOUND);
    }



    // Internal Implementations



    void update(int idx, java.util.function.IntUnaryOperator operation, Node node, int s, int t) {
        if (s == t) {
            node.value = operation.applyAsInt(node.value);
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        if (idx <= c)   update(idx, operation, node.lc, s, c);
        else            update(idx, operation, node.rc, c + 1, t);
        node.value = pushUp.applyAsInt(node.lc.value, node.rc.value);
    }

    void set(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.value = val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        if (idx <= c)   set(idx, val, node.lc, s, c);
        else            set(idx, val, node.rc, c + 1, t);
        node.value = pushUp.applyAsInt(node.lc.value, node.rc.value);
    }

    void add(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.value += val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        if (idx <= c)   add(idx, val, node.lc, s, c);
        else            add(idx, val, node.rc, c + 1, t);
        node.value = pushUp.applyAsInt(node.lc.value, node.rc.value);
    }

    void multiply(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.value *= val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        if (idx <= c)   multiply(idx, val, node.lc, s, c);
        else            multiply(idx, val, node.rc, c + 1, t);
        node.value = pushUp.applyAsInt(node.lc.value, node.rc.value);
    }

    int get(int idx, Node node, int s, int t) {
        if (s == t) {
            return node.value;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (idx <= c)   return node.lc == null ? DEFAULT_VALUE : get(idx, node.lc, s, c);
        else            return node.rc == null ? DEFAULT_VALUE : get(idx, node.rc, c + 1, t);
    }

    int query(int l, int r, Node node, int s, int t) {
        if (l <= s && t <= r) {
            return node.value;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (node.lc == null) {node.lc = new Node(); node.rc = new Node();}
        int ret = DEFAULT_VALUE;
        if (l <= c) ret = query(l, r, node.lc, s, c);
        if (c < r)  ret = pushUp.applyAsInt(ret, query(l, r, node.rc, c + 1, t));
        return ret;
    }
}