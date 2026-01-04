/**
 * 122. Best Time to Buy and Sell Stock II
 */
public class _122 {
    class Solution1_Greedy {
        // max(profit) = max(prices[j] - prices[i]) where j > i ==> for each j, find min(prices[i])
        public int maxProfit(int[] prices) {
            int minPrice = 10001, profit = 0;
            for (int price : prices) {
                if (price >= minPrice) {
                    profit += price - minPrice;
                    minPrice = price;
                } else {
                    minPrice = price;
                }
            }
            return profit;
        }
    }

    class Solution2_DP {
        public int maxProfit(int[] prices) {
            int n = prices.length;
            int[][] dp = new int[n + 1][2];
            dp[0][0] = 0;
            dp[0][1] = -10001;
            for (int i = 0; i < n; i++) {
                int day = i + 1;
                dp[day][0] = Math.max(dp[i][0], dp[i][1] + prices[i]);
                dp[day][1] = Math.max(dp[i][1], dp[i][0] - prices[i]);
            }
            return dp[n][0];
        }
    }

    class Solution3_DP_SpaceOptimized {
        public int maxProfit(int[] prices) {
            int n = prices.length, notHold = 0, hold = -prices[0] - 1;
            for (int day = 0; day < n; day++) {
                notHold = Math.max(notHold, hold + prices[day]);
                hold = Math.max(hold, notHold - prices[day]);
            }
            return notHold;
        }
    }
}
