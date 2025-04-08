/**
 * 714. Best Time to Buy and Sell Stock with Transaction Fee
 */
public class _714 {
    class Solution1_DP {
        public int maxProfit(int[] prices, int fee) {
            int n = prices.length;
            int[][] dp = new int[n + 1][2];
            dp[0][0] = 0;
            dp[0][1] = -50001; // integer overflow if we define as -INF
    
            for (int i = 0; i < n; i++) {
                int day = i + 1;
                dp[day][0] = Math.max(dp[i][0], dp[i][1] + prices[i] - fee); // charge when selling
                dp[day][1] = Math.max(dp[i][1], dp[i][0] - prices[i]); // we can also charge when buying
            }
            return dp[n][0];
        }
    }

    class Solution2_DP_Without_Memo {
        public int maxProfit(int[] prices, int fee) {
            int notHold = 0, hold = -50001;
            for (int price : prices) {
                notHold = Math.max(notHold, hold + price - fee);
                hold = Math.max(hold, notHold - price);
            }
            return notHold;
        }
    }
}
