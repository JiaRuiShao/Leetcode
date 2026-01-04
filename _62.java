/**
 * 62. Unique Paths
 * 
 * Clarification:
 * - Assume m & n > 0?
 * 
 * Followup:
 * - Obstacles in the grid? set dp[i][j] = 0
 * - You can also move diagonally? dp[i][j] = dp[i-1][j] + dp[i][j-1] + dp[i-1][j-1]
 * - Each cell has a cost and we want minimum cost? LC 64
 * - Can move in all 4 directions and want to find all paths? boolean[] to track visited; use backtrack
 */
public class _62 {
    // Time: O(mn)
    // Space: O(mn)
    class Solution1_DP {
        public int uniquePaths(int m, int n) {
            int[][] dp = new int[m][n]; // ways from (i, j) to reach (0, 0)
            // dp[i][j] = dp[i-1][j] + dp[i][j-1]
            for (int i = 0; i < m; i++) dp[i][0] = 1;
            for (int j = 0; j < n; j++) dp[0][j] = 1;
            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
            return dp[m - 1][n - 1];
        }
    }

    // Time: O(mn)
    // Space: O(min(m, n))
    class Solution1_DP_SpaceOptimized {
        public int uniquePaths(int m, int n) {
            if (n > m) return uniquePaths(n, m);
            int[] dp = new int[n]; // ways from (i, j) to reach (0, 0)
            // dp[i][j] = dp[i-1][j] + dp[i][j-1]
            for (int j = 0; j < n; j++) dp[j] = 1;

            for (int i = 1; i < m; i++) {
                // dp[0] = 1;
                for (int j = 1; j < n; j++) {
                    dp[j] = dp[j] + dp[j - 1];
                }
            }
            return dp[n - 1];
        }
    }

    // Time: O(mn)
    // Space: O(mn)
    class Solution2_Recursion_WithMemo {
        public int uniquePaths(int m, int n) {
            int[][] memo = new int[m][n];
            return dp(m - 1, n - 1, memo);
        }

        private int dp(int r, int c, int[][] memo) {
            if (r == 0 || c == 0) return 1;
            if (memo[r][c] > 0) return memo[r][c];
            memo[r][c] = dp(r - 1, c, memo) + dp(r, c - 1, memo);
            return memo[r][c];
        }
    }
}
