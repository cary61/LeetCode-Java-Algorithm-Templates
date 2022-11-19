/**
 * Preconditioning in O(n^2), check if any subsequence is palindrome in O(1).
 * 
 * @author cary61
 */
class PalindromeDP {

    /**
     * The length of string.
     */
    int length;

    /**
     * The space that dynamic programming using.
     */
    boolean[][] dp;

    /**
     * Initialize.
     * 
     * @param str the string
     */
    PalindromeDP(String str) {
        this.length = str.length();
        this.dp = new boolean[length][length];
        for (int i = 0; i < length; ++i) {
            dp[i][i] = true;
        }
        for (int i = 1; i < length; ++i) {
            if (str.charAt(i - 1) == str.charAt(i)) {
                dp[i - 1][i] = true;
            }
        }
        for (int d = 2; d < length; ++d) {
            for (int i = d; i < length; ++i) {
                if (dp[i - d + 1][i - 1] && str.charAt(i - d) == str.charAt(i)) {
                    dp[i - d][i] = true;
                }
            }
        }
    }

    /**
     * Check if string[l, r] is palindrome.
     * 
     * @param l the lower bound of range
     * @param r the upper bound of range
     * @return if the substring is palindrome.
     */
    boolean isPalindrome(int l, int r) {
        return dp[l][r];
    }
}