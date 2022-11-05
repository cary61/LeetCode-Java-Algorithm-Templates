/**
 * A PrefixXorSum model, maintains the range xor sum of an array.
 * Capital of getting the xor sum of range of an array in O(1) time.
 * 
 * @author cary61
 */
class PrefixXorSum {

    /**
     * An array that maintains the PrefixXorSum of the original array.
     */
    int[] pre;

    /**
     * Initialize a PrefixXorSum model of an array.
     * @param arr the array that will be maintained
     */
    PrefixXorSum(int[] arr) {
        int len = arr.length;
        this.pre = new int[len + 1];
        for (int i = 0; i < len; ++i) {
            pre[i + 1] = pre[i] ^ arr[i];
        }
    }

    /**
     * Get the xor sum of range[l, r] of the array.
     * @param l the lower-bound of range
     * @param r the upper-bound of range
     * @return the sum of range of the array
     */
    int sum(int l, int r) {
        return pre[r + 1] ^ pre[l];
    }
}