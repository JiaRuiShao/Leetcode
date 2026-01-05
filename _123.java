/**
 * 123. Best Time to Buy and Sell Stock III
 * 
 * - S1: BF O(n^2), O(1)
 * - S2: Greedy two pass with prefix & suffix profit array O(n), O(n)
 * - S3: DP O(n), O(n)/O(1)
 * 
 * Clarification:
 * - Can I buy & sell on the same day?
 * - Can I assume prices are non-negative?
 */
public class _123 {
    class Solution1_Greedy_TwoPass_SuffixPrefix {
        // prefix profit & suffix profit
        public int maxProfit(int[] prices) {
            int n = prices.length;
            int[] prefix = new int[n]; // max profit from [0, i]
            int[] suffix = new int[n]; // max profit from [i, n-1]

            // max(price[j] - price[i]) ==> given price[j], find min(price[i]) where i < j
            int minPrice = prices[0], maxProfit = 0;
            for (int i = 0; i < n; i++) {
                int price = prices[i];
                maxProfit = Math.max(maxProfit, price - minPrice);
                prefix[i] = maxProfit;
                minPrice = Math.min(minPrice, price);
            }

            // max(price[j] - price[i]) ==> given price[i], find max(price[j]) where i < j
            int maxPrice = prices[n - 1];
            maxProfit = 0;
            for (int i = n - 1; i >= 0; i--) {
                int price = prices[i];
                maxProfit = Math.max(maxProfit, maxPrice - price);
                suffix[i] = maxProfit;
                maxPrice = Math.max(maxPrice, price);
            }

            maxProfit = 0;
            for (int i = 0; i < n; i++) {
                maxProfit = Math.max(maxProfit, prefix[i] + suffix[i]); // notice here we assume we CAN sell & buy at same day; however here this requirement doesn't change result
            }
            return maxProfit;
        }
    }

    class Solution1_DP {
        public int maxProfit(int[] prices) {
            int n = prices.length;
            // dp[i][k][0/1] = max profit when not hold/hold a stock with at most k-1 transactions on day i-1
            // i = day, k = hold at most k transactions, 0/1 = not holding/holding
            int[][][] dp = new int[n+1][3][2];
            for (int k = 0; k <= 2; k++) {
                dp[0][k][0] = 0;
                dp[0][k][1] = -prices[0] - 1; // not yet bought, Integer.MIN_VALUE / 2 is preferred here
            }

            for (int i = 1; i <= n; i++) {
                int price = prices[i - 1];
                for (int k = 1; k <= 2; k++) {
                    dp[i][k][0] = Math.max(dp[i-1][k][0], dp[i-1][k][1] + price);
                    dp[i][k][1] = Math.max(dp[i-1][k][1], dp[i-1][k-1][0] - price);
                }
            }
            return dp[n][2][0];
        }
    }

    class Solution2_DP_SpaceOptimized {
        public int maxProfit(int[] prices) {
            int notHoldT1 = 0, notHoldT2 = 0, holdT1 = -prices[0] - 1, holdT2 = -prices[0] - 1;
            for (int price : prices) {
                int temp = notHoldT1; // yesterday's max profit not holding a stock with at most 1 transaction
                // here we can use today's notHoldT1 to calc today's holdT2, whether we can sell & buy at same day doesn't matter for the result, but it's good to know the semantic difference
                notHoldT1 = Math.max(notHoldT1, holdT1 + price);
                holdT1 = Math.max(holdT1, -price);
                notHoldT2 = Math.max(notHoldT2, holdT2 + price);
                holdT2 = Math.max(holdT2, temp - price);
            }
            return notHoldT2;
        }
    }
}
