/**
 * 188. Best Time to Buy and Sell Stock IV
 */
public class _188 {
    // dp[i][k][0/1] = maxProfit on day i-1 with at most k transactions allowed when not holding/holding a stock
    // dp[i][k][0] = max(dp[i-1][k][0], dp[i-1][k][1] + prices[i-1])
    // dp[i][k][1] = max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i-1])
    class Solution1_DP {
        public int maxProfit(int k, int[] prices) {
            int n = prices.length;
            int[][][] dp = new int[n+1][k+1][2];
            for (int j = 0; j <= k; j++) {
                dp[0][j][1] = Integer.MIN_VALUE / 2;
            }

            for (int i = 1; i <= n; i++) {
                int price = prices[i - 1];
                for (int j = 1; j <= k; j++) {
                    dp[i][j][0] = Math.max(dp[i-1][j][0], dp[i-1][j][1] + price);
                    dp[i][j][1] = Math.max(dp[i-1][j][1], dp[i-1][j-1][0] - price);
                }
            }

            return dp[n][k][0];
        }
    }

    // Time: O(nk)
    // Space: O(k)
    // dp[j][0] = max(dp[j][0], dp[j][1] + prices[i])
    // not holding: either stayed not holding, or sold today
    // dp[j][1] = max(dp[j][1], dp[j-1][0] - prices[i])
    // holding: either kept holding, or bought today (uses j-1 transactions)
    class Solution2_DP_SpaceOptimized {
        public int maxProfit(int k, int[] prices) {
            int n = prices.length;
            int[][] dp = new int[k+1][2];
            for (int j = 0; j <= k; j++) {
                dp[j][1] = Integer.MIN_VALUE / 2;
            }

            for (int i = 1; i <= n; i++) {
                int price = prices[i - 1], prev = 0;
                for (int j = 1; j <= k; j++) {
                    int temp = dp[j][0];
                    dp[j][0] = Math.max(dp[j][0], dp[j][1] + price);
                    dp[j][1] = Math.max(dp[j][1], prev - price);
                    prev = temp;
                }
            }

            return dp[k][0];
        }
    }

    class StockQuestionExtension {
        // Suppose we have transaction limit, cooldown period and transaction fee
        int maxProfit(int K, int[] prices, int cooldown, int fee) {
            int n = prices.length;
            if (n <= 0) {
                return 0;
            }
            if (K > n / 2) {
                // became a question without any transaction limitation
                return maxProfitWithInfK(prices, cooldown, fee);
            }

            int[][][] dp = new int[n + 1][K][2];
            // base cases
            for (int k = 0; k < K; k++) {
                dp[0][k][0] = 0;
                dp[0][k][1] = -prices[0] - fee; // prevent integer overflow
            }

            for (int day = 0; day < n; day++)
                for (int k = 0; k < K; k++) {
                    dp[day + 1][k][0] = Math.max(dp[day][k][0], dp[day][k][1] + prices[day]);
                    if (day - cooldown < 0) {
                        dp[day + 1][k][1] = Math.max(dp[day][k][1], -prices[day] - fee);
                    } else {
                        dp[day + 1][k][1] = Math.max(dp[day][k][1], dp[day-cooldown][k-1][0] - prices[day] - fee);     
                    }
                }
            return dp[n][K - 1][0];
        }

        int maxProfitWithInfK(int[] prices, int cooldown, int fee) {
            int n = prices.length;
            int[][] dp = new int[n + 1][2];
            dp[0][0] = 0;
            dp[0][1] = -prices[0] - fee;
            for (int day = 0; day < n; day++) {
                dp[day + 1][0] = Math.max(dp[day][0], dp[day][1] + prices[day]);
                if (day - cooldown < 0) {
                    dp[day + 1][1] = Math.max(dp[day][1], - prices[day]);
                } else {
                    dp[day + 1][1] = Math.max(dp[day][1], dp[day - cooldown][0] - prices[day]);
                }
            }
            return dp[n][0];
        }
    }
}
