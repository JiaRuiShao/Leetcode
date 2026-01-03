import java.util.Arrays;

/**
 * 64. Minimum Path Sum
 */
public class _64 {
    class Solution1_DP_2D {
        public int minPathSum(int[][] grid) {
            int m = grid.length, n = grid[0].length;
            // dp[i][j] = min path sum from i, j to 0, 0
            //          = min(dp[i-1][j], dp[i][j-1]) + grid[i][j]
            // dp[0][j] = max(grid[0][0..j])
            // dp[i][0] = max(grid[0..i][0])
            int[][] dp = new int[m][n];
            dp[0][0] = grid[0][0];
            for (int j = 1; j < n; j++) {
                dp[0][j] = dp[0][j - 1] + grid[0][j];
            }
            for (int i = 1; i < m; i++) {
                dp[i][0] = dp[i - 1][0] + grid[i][0];
            }
            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
                }
            }
            return dp[m - 1][n - 1];
        }
    }

    class Solution2_DP_1D {
        public int minPathSum(int[][] grid) {
            int m = grid.length, n = grid[0].length;
            int[] dp = new int[n];
            dp[0] = grid[0][0];
            for (int j = 1; j < n; j++) {
                dp[j] = dp[j - 1] + grid[0][j];
            }
            for (int i = 1; i < m; i++) {
                dp[0] += grid[i][0];
                for (int j = 1; j < n; j++) {
                    dp[j] = Math.min(dp[j], dp[j - 1]) + grid[i][j];
                }
            }
            return dp[n - 1];
        }
    }

    class Solution3_Recursion_WithMemo {
        public int minPathSum(int[][] grid) {
            int m = grid.length, n = grid[0].length;
            int[][] memo = new int[m][n];
            for (int i = 0; i < m; i++) {
                Arrays.fill(memo[i], -1);
            }
            return dp(grid, m - 1, n - 1, memo);
        }

        private int dp(int[][] grid, int r, int c, int[][] memo) {
            if (r == 0 && c == 0) return grid[r][c];
            if (memo[r][c] != -1) return memo[r][c];
            int cost = Integer.MAX_VALUE;
            if (r > 0) cost = Math.min(cost, dp(grid, r - 1, c, memo));
            if (c > 0) cost = Math.min(cost, dp(grid, r, c - 1, memo));
            memo[r][c] = cost + grid[r][c]; // cost would never be Integer.MAX_VALUE, no need to check integer overflow
            return memo[r][c];
        }
    }
}
