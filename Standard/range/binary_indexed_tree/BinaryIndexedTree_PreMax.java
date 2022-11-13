/**
 * A BinaryIndexTree, maintains the max of range.
 * Capable of adding value to single points, and get the max of the query range starting from 1.
 * 
 * @author cary61
 */
class BinaryIndexedTree_PreMax {

    /**
     * The length of original array.
     */
    int length;

    /**
     * The original array.
     */
    int[] arr;

    /**
     * The array that maintains prefix max;
     */
    int[] tree;

    public BinaryIndexedTree_PreMax(int[] arr) {
        this.length = arr.length;
        this.arr = arr;
        this.tree = new int[length + 1];
        init();
    }

    /**
     * Add a value to single point.
     * 
     * @param idx the index of single point
     * @param val the value that will be added to single point
     */
    public void add(int idx, int val) {
        arr[idx] += val;
        for (int i = idx + 1; i <= length; i += i & -i) {
            tree[i] = Math.max(tree[i], arr[idx]);
        }
    }

    /**
     * Get the prefix max of range[0,index].
     * 
     * @param idx the end point of range
     * @return the prefix max
     */
    public int premax(int idx) {
        int ret = 0;
        for (int i = idx + 1; i > 0; i -= i & -i) {
            ret = Math.max(ret, tree[i]);
        }
        return ret;
    }



    // Internal implementations



    /**
     * Initialize the BinaryIndexedTree
     */
    void init() {
        for (int i = 1; i <= length; ++i) {
            tree[i] = Math.max(tree[i], arr[i - 1]);
            int j = i + (i & -i);
            if (j <= length) {
                tree[j] = Math.max(tree[j], tree[i]);
            }
        }
    }
}