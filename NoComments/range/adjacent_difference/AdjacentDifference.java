class AdjacentDifference {
    
    int length;
    int[] diff;
    int[] res;
    int time;
    int timeStamp;
    
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
    
    void add(int l, int r, int val) {
        diff[l] += val;
        diff[r + 1] -= val;
        ++time;
    }
    
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