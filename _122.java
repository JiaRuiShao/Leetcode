/**
 * 122. Best Time to Buy and Sell Stock II
 */
public class _122 {
    class Solution1_Two_Pointers {
        public int maxProfit(int[] prices) {
            int left = 0, right = 0, totalProfit = 0;
            int n = prices.length;
            while (++right < n) {
                int profit = prices[right] - prices[left];
                if (profit <= 0) {
                    left = right; // should buy today since the price is lower
                } else {
                    totalProfit += profit;
                    left = right; // able to buy on the same day of selling
                }
            }
            return totalProfit;
        }
    }

    class Solution2_DP_With_Memo {
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

    class Solution3_DP_Without_Memo {
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
