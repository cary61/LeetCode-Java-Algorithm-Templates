/**
 * A PrefixXorSum model, maintains the range xor sum of an 2D array.
 * Capital of getting the xor sum of range of an array in O(1) time.
 * 
 * @author cary61
 */
class PrefixXorSum_2D {
    
    /**
     * An array that maintains the PrefixXorSum of the original 2D array.
     */
    int[][] pre;

    /**
     * Initialize a PrefixXorSum model of an array.
     * @param arr the array that will be maintained
     */
    PrefixXorSum_2D(int[][] arr) {
        int m = arr.length;
        int n = arr[0].length;
        this.pre = new int[m + 1][n + 1];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                pre[i + 1][j + 1] = pre[i + 1][j] ^ pre[i][j + 1] ^ pre[i][j] ^ arr[i][j];
            }
        }
    }

    /**
     * Get the xor sum of range[l1, r1][l2, r2] of the 2D array.
     * @param l1 the x of top-left corner of range
     * @param r1 the y of top-left corner of range
     * @param l2 the x of bottom-right corner of range
     * @param r2 the y of bottom-right corner of range
     * @return the xor sum of range of the array
     */
    int sum(int l1, int r1, int l2, int r2) {
        return pre[l2 + 1][r2 + 1] ^ pre[l2 + 1][r1] ^ pre[l1][r2 + 1] ^ pre[l1][r1];
    }
}
