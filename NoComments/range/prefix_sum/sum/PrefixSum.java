class PrefixSum {

    int[] pre;

    PrefixSum(int[] arr) {
        int len = arr.length;
        this.pre = new int[len + 1];
        for (int i = 0; i < len; ++i) {
            pre[i + 1] = pre[i] + arr[i];
        }
    }

    int sum(int l, int r) {
        return pre[r + 1] - pre[l];
    }
}