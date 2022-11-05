/**
 * A PrefixSum model, maintains the range sum of an array.
 * Capital of getting the sum of range of an array in O(1) time.
 * 
 * @author cary61
 */
class PrefixSum {

    /**
     * An array that maintains the PrefixSum of the original array.
     */
    int[] pre;

    /**
     * Initialize a PrefixSum model of an array.
     * @param arr the array that will be maintained
     */
    PrefixSum(int[] arr) {
        int len = arr.length;
        this.pre = new int[len + 1];
        for (int i = 0; i < len; ++i) {
            pre[i + 1] = pre[i] + arr[i];
        }
    }

    /**
     * Get the sum of range[l, r] of the array.
     * @param l the lower-bound of range
     * @param r the upper-bound of range
     * @return the sum of range of the array
     */
    int sum(int l, int r) {
        return pre[r + 1] - pre[l];
    }
}