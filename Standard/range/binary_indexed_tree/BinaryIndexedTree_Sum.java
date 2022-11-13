class BinaryIndexedTree_Sum {

    /**
     * The length of original array.
     */
    int length;

    /**
     * The original array.
     */
    int[] arr;

    /**
     * The array that maintains prefix sum;
     */
    int[] tree;

    public BinaryIndexedTree_Sum(int[] arr) {
        this.length = arr.length;
        this.arr = arr;
        this.tree = new int[length + 1];
        init();
    }

    /**
     * Set a single point a new value.
     * 
     * @param idx the index of single point
     * @param val the new value
     */
    public void set(int idx, int val) {
        add(idx, val - arr[idx]);
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
            tree[i] += val;
        }
    }

    /**
     * Get the sum of range[l, r].
     * 
     * @param l the lower bound of range
     * @param r the upper bound of range
     * @return the sum of range
     */
    public int sum(int l, int r) {
        return presum(r) - presum(l - 1);
    }

    /**
     * Get the prefix sum of range[0,index].
     * 
     * @param idx the end point of range
     * @return the prefix sum
     */
    public int presum(int idx) {
        int ret = 0;
        for (int i = idx + 1; i > 0; i -= i & -i) {
            ret += tree[i];
        }
        return ret;
    }



    // Internal implementations



    /**
     * Initialize the BinaryIndexedTree
     */
    void init() {
        for (int i = 1; i <= length; ++i) {
            tree[i] += arr[i - 1];
            int j = i + (i & -i);
            if (j <= length) {
                tree[j] += tree[i];
            }
        }
    }
}