/**
 * An AdjacentDifference array, capable of range adding and reducing.
 * 
 * @author cary61
 */
class AdjacentDifference {

    /**
     * The length of original array.
     */
    int length;

    /**
     * The adjacent difference array.
     */
    int[] diff;

    /**
     * The result array.
     */
    int[] res;

    /**
     * Maintaining the number of operations.
     */
    int time;

    /**
     * The last time of getting result array.
     */
    int timeStamp;

    /**
     * Initialize the adjacent difference array.
     * 
     * @param arr the original array
     */
    AdjacentDifference(int[] arr) {
        this.length = arr.length;
        this.diff = new int[length + 1];
        diff[0] = arr[0];
        for (int i = 1; i < length; ++i) {
            diff[i] = arr[i] - arr[i - 1];
        }
        this.res = new int[length];
        this.time = 0;
        this.timeStamp = -1;
    }

    /**
     * Add value to range.
     * 
     * @param l the lower bound of range
     * @param r the upper bound of range
     * @param val the value that will be added to range
     */
    void add(int l, int r, int val) {
        diff[l] += val;
        diff[r + 1] -= val;
        ++time;
    }

    /**
     * Get the result array.
     * 
     * @return the result array
     */
    int[] getResult() {
        if (time == timeStamp) {
            return res;
        }
        res[0] = diff[0];
        for (int i = 1; i < length; ++i) {
            res[i] = res[i - 1] + diff[i];
        }
        timeStamp = time;
        return res;
    }
}