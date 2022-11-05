class PrefixXorSum_2D {
    
    int[][] pre;

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

    int sum(int l1, int r1, int l2, int r2) {
        return pre[l2 + 1][r2 + 1] ^ pre[l2 + 1][r1] ^ pre[l1][r2 + 1] ^ pre[l1][r1];
    }
}
