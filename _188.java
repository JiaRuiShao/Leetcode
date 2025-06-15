/**
 * 188. Best Time to Buy and Sell Stock IV
 */
public class _188 {
    class Solution1_DP {
        public int maxProfit(int k, int[] prices) {
            int n = prices.length;
            int[][][] profits = new int[n + 1][k][2];
            for (int transCount = 0; transCount < k; transCount++) {
                profits[0][transCount][0] = 0;
                profits[0][transCount][1] = -prices[0] - 1;
            }
            for (int i = 0; i < n; i++) {
                int day = i + 1;
                for (int transCount = 0; transCount < k; transCount++) {
                    profits[day][transCount][0] = Math.max(profits[day - 1][transCount][0], profits[day - 1][transCount][1] + prices[i]);
                    if (transCount > 0) {
                        profits[day][transCount][1] = Math.max(profits[day - 1][transCount][1], profits[day - 1][transCount - 1][0] - prices[i]);
                    } else {
                        profits[day][transCount][1] = Math.max(profits[day - 1][transCount][1], - prices[i]); // here we CANNOT use profits[day - 1][0][0] - prices[i] because maxTransCount defined here is 0 and profits[day - 1][0][0] represents the profit of having at most k stocks but right now it's holding none
                    }
                }
            }
            return profits[n][k - 1][0];
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
