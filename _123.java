/**
 * 123. Best Time to Buy and Sell Stock III
 */
public class _123 {
    class Solution1_DP {
        public int maxProfit(int[] prices) {
            int n = prices.length;
            int[][][] dp =  new int[n + 1][2][2];
            // base cases
            dp[0][0][0] = 0;
            dp[0][0][1] = -100001;
            dp[0][1][0] = 0;
            dp[0][1][1] = -100001;
            // transitions
            for (int day = 0; day < n; day++) {
                dp[day + 1][0][0] = Math.max(dp[day][0][0], dp[day][0][1] + prices[day]);
                dp[day + 1][0][1] = Math.max(dp[day][0][1], - prices[day]);
                dp[day + 1][1][0] = Math.max(dp[day][1][0], dp[day][1][1] + prices[day]);
                dp[day + 1][1][1] = Math.max(dp[day][1][1], dp[day][0][0] - prices[day]);
            }
            return dp[n][1][0];
        }
    }

    class Solution2_DP_Without_Memo {
        public int maxProfit(int[] prices) {
            int notHoldT1 = 0, holdT1 = -100001, notHoldT2 = 0, holdT2 = -100001;
            for (int price : prices) {
                // dp[day + 1][0][0] = Math.max(dp[day][0][0], dp[day][0][1] + prices[day]);
                notHoldT1 = Math.max(notHoldT1, holdT1 + price);
                // dp[day + 1][0][1] = Math.max(dp[day][0][1], - prices[day]);
                holdT1 = Math.max(holdT1, -price);
                // dp[day + 1][1][0] = Math.max(dp[day][1][0], dp[day][1][1] + prices[day]);
                notHoldT2 = Math.max(notHoldT2, holdT2 + price);
                // dp[day + 1][1][1] = Math.max(dp[day][1][1], dp[day][0][0] - prices[day]);
                holdT2 = Math.max(holdT2, notHoldT1 - price);
            }
            return notHoldT2;
        }
    }
}
