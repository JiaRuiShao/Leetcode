/**
 * 309. Best Time to Buy and Sell Stock with Cooldown
 */
public class _309 {
    class Solution0_DFS_Brute_Force {
        public int maxProfit(int[] prices) {
            return dfs(prices, 0, false);
        }
    
        private int dfs(int[] prices, int day, boolean holding) {
            if (day >= prices.length) return 0;
    
            int doNothing = dfs(prices, day + 1, holding); // cooldown / skip
            int doSomething;
    
            if (holding) {
                // Option to sell
                doSomething = prices[day] + dfs(prices, day + 2, false); // cooldown next day
            } else {
                // Option to buy
                doSomething = -prices[day] + dfs(prices, day + 1, true);
            }
    
            return Math.max(doNothing, doSomething);
        }
    }

    class Solution1_DP {
        public int maxProfit(int[] prices) {
            int n = prices.length;
            int[][] dp = new int[n + 1][2];
            dp[0][0] = 0;
            dp[0][1] = -1001; // -(max stock price + 1)
            for (int i = 0; i < n; i++) {
                int day = i + 1;
                dp[day][0] = Math.max(dp[i][0], dp[i][1] + prices[i]);
                if (i - 1 < 0) { // prevent array index out of bound error
                    dp[day][1] = Math.max(dp[i][1], dp[i][0] - prices[i]);
                } else {
                    dp[day][1] = Math.max(dp[i][1], dp[i - 1][0] - prices[i]);
                }
            }
            return dp[n][0];
        }
    }

    class Solution2_DP_Without_Memo {
        public int maxProfit(int[] prices) {
            int notHold = 0, prevNotHold = 0, hold = Integer.MIN_VALUE; // Equivalent to -infinity or -1001
    
            for (int price : prices) {
                int temp = notHold;
                notHold = Math.max(notHold, hold + price);
                hold = Math.max(hold, prevNotHold - price);
                prevNotHold = temp;
            }
    
            return notHold;
        }
    }
}
