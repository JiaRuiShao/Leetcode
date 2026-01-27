import java.util.Arrays;

/**
 * 1335. Minimum Difficulty of a Job Schedule
 */
public class _1335 {
    class Solution1_DP {
        // DP partition
        // split array into d parts with cost = max job difficulty that day
        // goal: min(totalCost) = min(sum(cost[1..d]))
        public int minDifficulty(int[] jobDifficulty, int d) {
            int n = jobDifficulty.length;
            if (n < d) return -1;
            // dp[i][j] = min cost to finish first i jobs in j days
            // dp[0][0] = 0
            // dp[1..n][0] = INF
            int[][] dp = new int[n + 1][d + 1];
            for (int i = 0; i <= n; i++) {
                Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
            }
            dp[0][0] = 0;
            // j <= min(i, d)
            // dp[i][j] = min(dp[k][j-1] + max(jobDifficulty[k..i-1])) for k in [j-1, i-1]
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= Math.min(i, d); j++) {
                    int maxDifficulty = 0; // today's difficulty on jth day
                    // jobs to finish today
                    for (int k = i - 1; k >= j - 1; k--) {
                        maxDifficulty = Math.max(maxDifficulty, jobDifficulty[k]);
                        dp[i][j] = Math.min(dp[i][j], dp[k][j - 1] + maxDifficulty);
                    }
                }
            }
            return dp[n][d];
        }

        class Solution2 {
            public int minDifficulty(int[] jobDifficulty, int d) {
                int n = jobDifficulty.length;
                if (d > n) return -1;
                int[][] dp = new int[d + 1][n];
                dp[d][n - 1] = jobDifficulty[n - 1];
                for (int i = n - 2; i >= 0; i--) dp[d][i] = Math.max(jobDifficulty[i], dp[d][i + 1]);

                for (int day = d - 1; day > 0; day--) {
                    for (int i = day - 1; i < n - (d - day); i++) {
                        int hardest = 0;
                        int min = Integer.MAX_VALUE;
                        for (int j = i; j < n - (d - day); j++) {
                            hardest = Math.max(hardest, jobDifficulty[j]);
                            min = Math.min(min, hardest + dp[day + 1][j + 1]);
                        }
                        dp[day][i] = min;
                    }
                }

                return dp[1][0];
            }
        }
    }
} 