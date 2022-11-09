/**
 * A SegmentTree, maintains the leatest common multiple of range.
 * Capital of updating new value, adding value to single points, multiplying value to single points, and getting the leatest common multiple of any query range.
 * Implemented by Node.
 * The public methods are advised to use.
 *
 * @author cary61
 */
class SegmentTree_LCM {

    /**
     * The node of tree structure.
     */
    class Node {

        /**
         * The leatest common multiple of the range that this node represents.
         */
        int lcm;

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
     * The original array.
     */
    int[] arr;

    /**
     * Instantiate a SegmentTree that maintains an array.
     *
     * @param arr The array that SegmentTree maintains
     */
    public SegmentTree_LCM(int[] arr) {
        this.LOWERBOUND = 0;
        this.UPPERBOUND = arr.length - 1;
        this.root = new Node();
        this.arr = arr;
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
     * Get the value of a single point.
     *
     * @param idx the index of the single point
     * @return the value of the single point
     */
    public int get(int idx) {
        return arr[idx];
    }

    /**
     * Get the leatest common multiple of range [l, r].
     *
     * @param l the lower-bound of query range
     * @param r the upper-bound of query range
     * @return the sum of query range [l, r]
     */
    public int lcm(int l, int r) {
        return lcm(l, r, root, LOWERBOUND, UPPERBOUND);
    }



    // Internal Implementations

    

    void set(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.lcm = val;
            arr[s] = val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (idx <= c)   set(idx, val, node.lc, s, c);
        else            set(idx, val, node.rc, c + 1, t);
        node.lcm = getLcm(node.lc.lcm, node.rc.lcm);
    }

    void add(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.lcm += val;
            arr[s] += val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (idx <= c)   add(idx, val, node.lc, s, c);
        else            add(idx, val, node.rc, c + 1, t);
        node.lcm = getLcm(node.lc.lcm, node.rc.lcm);
    }

    void multiply(int idx, int val, Node node, int s, int t) {
        if (s == t) {
            node.lcm *= val;
            arr[s] *= val;
            return;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        if (idx <= c)   multiply(idx, val, node.lc, s, c);
        else            multiply(idx, val, node.rc, c + 1, t);
        node.lcm = getLcm(node.lc.lcm, node.rc.lcm);
    }

    int lcm(int l, int r, Node node, int s, int t) {
        if (l <= s && t <= r) {
            return node.lcm;
        }
        int c = (s & t) + ((s ^ t) >> 1);
        int ret = -1;
        if (l <= c) ret = lcm(l, r, node.lc, s, c);
        if (c < r)  ret = ret == -1 ? lcm(l, r, node.rc, c + 1, t): getLcm(ret, lcm(l, r, node.rc, c + 1, t));
        return ret;
    }

    void build(int[] arr, Node node, int s, int t) {
        if (s == t) {
            node.lcm = arr[s];
            return;
        }
        int c = (s & t) +((s ^ t) >> 1);
        node.lc = new Node();
        node.rc = new Node();
        build(arr, node.lc, s, c);
        build(arr, node.rc, c + 1, t);
        node.lcm = getLcm(node.lc.lcm, node.rc.lcm);
    }

    int getGcd(int a, int b) {
        int m;
        while (b != 0) {
            m = a % b;
            a = b;
            b = m;
        }
        return a;
    }

    int getLcm(int a, int b) {
        return a / getGcd(a, b) * b;
    }
}
