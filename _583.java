import java.util.Arrays;

/**
 * 583. Delete Operation for Two Strings
 * 
 * - S1: Recursion with memo O(mn), O(mn)
 * - S2: Bottom-Up 2D DP O(mn), O(mn)
 * - S3: Bottom-Up 1D DP O(mn) O(min(m, n))
 */
public class _583 {
    class Solution1_DP_2D {
        // dp[i][j] = min delete make s1[0..i-1] == s2[0...j-1]
        // dp[i][j] = dp[i-1][j-1] when s1[i-1] == s2[j-1]
        // dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + 1 when s1[i-1] ≠ s2[j-1]
        public int minDistance(String word1, String word2) {
            int m = word1.length(), n = word2.length();
            int[][] dp = new int[m + 1][n + 1];
            for (int i = 0; i <= m; i++) dp[i][0] = i;
            for (int j = 0; j <= n; j++) dp[0][j] = j;

            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = 1 + Math.min(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }
            return dp[m][n];
        }
    }

    class Solution2_DP_1D {
        // dp[i][j] = min delete make s1[0..i-1] == s2[0...j-1]
        // dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + 1 when s1[i-1] ≠ s2[j-1]
        public int minDistance(String word1, String word2) {
            int m = word1.length(), n = word2.length();
            if (n > m) return minDistance(word2, word1);
            int[] dp = new int[n + 1];
            for (int j = 0; j <= n; j++) dp[j] = j;

            for (int i = 1; i <= m; i++) {
                int prev = i - 1; // dp[i-1][j-1]
                dp[0] = i; // dp[i][j-1]
                for (int j = 1; j <= n; j++) {
                    int temp = dp[j]; // dp[i-1][j]
                    if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                        dp[j] = prev;
                    } else {
                        dp[j] = 1 + Math.min(dp[j], dp[j - 1]);
                    }
                    prev = temp;
                }
            }
            return dp[n];
        }
    }

    class Solution3_Recursion_WithMemo {
        public int minDistance(String word1, String word2) {
            int[][] memo = new int[word1.length()][word2.length()];
            for (int i = 0; i < word1.length(); i++) Arrays.fill(memo[i], -1);
            return dp(word1, word1.length() - 1, word2, word2.length() - 1, memo);
        }

        private int dp(String s1, int i, String s2, int j, int[][] memo) {
            if (i == -1) return j + 1;
            if (j == -1) return i + 1;
            if (s1.charAt(i) == s2.charAt(j)) return dp(s1, i - 1, s2, j - 1, memo);
            if (memo[i][j] != -1) return memo[i][j];
            memo[i][j] = Math.min(dp(s1, i - 1, s2, j, memo), dp(s1, i, s2, j - 1, memo)) + 1;
            return memo[i][j];
        }
    }
}
