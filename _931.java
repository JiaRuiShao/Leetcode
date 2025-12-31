import java.util.Arrays;

/**
 * 931. Minimum Falling Path Sum
 * 
 * - S1: Top-Down Recursion O(3^n) without memo, O(n)
 * - S2: Bottom-Up Iterative DP O(n^2), O(n^2) without optimization, O(n) with optimization
 * - S3: In-place DP O(n^2), O(1)
 * 
 * Followup:
 * - What if we can move in 4 directions (up, down, left, right)? Then we need a visited boolean[][] to track to avoid cycle
 */
public class _931 {
    // Time: O(3^n)
    // Space: O(n)
    class Solution0_Backtrack_TLE {
        public int minFallingPathSum(int[][] matrix) {
            int minSum = Integer.MAX_VALUE;
            for (int col = 0; col < matrix.length; col++) {
                minSum = Math.min(minSum, backtrack(matrix, 0, col));
            }
            return minSum;
        }

        private int backtrack(int[][] mtx, int row, int col) {
            if (row == mtx.length) return 0;
            int minSum = backtrack(mtx, row + 1, col);
            if (col >= 1) minSum = Math.min(minSum, backtrack(mtx, row + 1, col - 1));
            if (col < mtx.length - 1) minSum = Math.min(minSum, backtrack(mtx, row + 1, col + 1));
            minSum += mtx[row][col];
            return minSum;
        }
    }

    // Time: O(n^2)
    // Space: O(n^2)
    class Solution1_Backtrack_WithMemo {
        private int defaultVal = 10001;
        public int minFallingPathSum(int[][] matrix) {
            int minSum = Integer.MAX_VALUE, n = matrix.length;
            int[][] memo = new int[n][n];
            for (int r = 0; r < n; r++) {
                Arrays.fill(memo[r], defaultVal);
            }
            for (int col = 0; col < n; col++) {
                minSum = Math.min(minSum, backtrack(matrix, 0, col, memo));
            }
            return minSum;
        }

        private int backtrack(int[][] mtx, int row, int col, int[][] memo) {
            if (row == mtx.length) return 0;
            if (memo[row][col] != defaultVal) return memo[row][col];
            int minSum = backtrack(mtx, row + 1, col, memo);
            if (col >= 1) minSum = Math.min(minSum, backtrack(mtx, row + 1, col - 1, memo));
            if (col < mtx.length - 1) minSum = Math.min(minSum, backtrack(mtx, row + 1, col + 1, memo));
            minSum += mtx[row][col];
            return memo[row][col] = minSum;
        }
    }

    class Solution2_DP_2D {
        // dp[i][j] = min(dp[i+1][j-1], dp[i+1][j+1], dp[i+1][j]) + matrix[i][j]
        public int minFallingPathSum(int[][] matrix) {
            int n = matrix.length, minSum = Integer.MAX_VALUE;
            int[][] dp = new int[n][n]; // minimum sum of failing path ends at (i, j)

            for (int r = n - 1; r >= 0; r--) {
                for (int c = 0; c < n; c++) {
                    dp[r][c] = matrix[r][c] + getMinElemInNextRow(dp, r, c);
                }
            }

            for (int c = 0; c < n; c++) {
                minSum = Math.min(minSum, dp[0][c]);
            }
            return minSum;
        }

        private int getMinElemInNextRow(int[][] dp, int r, int c) {
            int n = dp.length;
            if (r == n - 1) return 0;
            int min = dp[r + 1][c];
            if (c - 1 >= 0) min = Math.min(min, dp[r + 1][c - 1]);
            if (c + 1 < n) min = Math.min(min, dp[r + 1][c + 1]);
            return min;
        }
    }

    class Solution2_DP_2D_InPlace {
        public int minFallingPathSum(int[][] matrix) {
            int n = matrix.length, minSum = Integer.MAX_VALUE;
            for (int r = n - 1; r >= 0; r--) {
                for (int c = 0; c < n; c++) {
                    matrix[r][c] += getMinElemInNextRow(matrix, r, c);
                }
            }

            for (int c = 0; c < n; c++) {
                minSum = Math.min(minSum, matrix[0][c]);
            }
            return minSum;
        }

        private int getMinElemInNextRow(int[][] matrix, int r, int c) {
            int n = matrix.length;
            if (r == n - 1) return 0;
            int min = matrix[r + 1][c];
            if (c - 1 >= 0) min = Math.min(min, matrix[r + 1][c - 1]);
            if (c + 1 < n) min = Math.min(min, matrix[r + 1][c + 1]);
            return min;
        }
    }

    class Solution3_DP_1D {
        // dp[i] = min(dp[i-1], dp[i+1], dp[i]) + matrix[i][j]
        public int minFallingPathSum(int[][] matrix) {
            int n = matrix.length, minSum = Integer.MAX_VALUE;
            int[] prev = new int[n];

            for (int r = n - 1; r >= 0; r--) {
                int[] curr = new int[n];
                for (int c = 0; c < n; c++) {
                    curr[c] = matrix[r][c] + getMinElemInNextRow(prev, r, c);
                }
                prev = curr;
            }

            for (int c = 0; c < n; c++) {
                minSum = Math.min(minSum, prev[c]);
            }
            return minSum;
        }

        private int getMinElemInNextRow(int[] dp, int r, int c) {
            int n = dp.length;
            if (r == n - 1) return 0;
            int min = dp[c];
            if (c - 1 >= 0) min = Math.min(min, dp[c - 1]);
            if (c + 1 < n) min = Math.min(min, dp[c + 1]);
            return min;
        }
    }
}
