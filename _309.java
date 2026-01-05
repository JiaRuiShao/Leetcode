/**
 * 309. Best Time to Buy and Sell Stock with Cooldown
 * 
 * Followup:
 * - What if cooldown is d days instead of 1? Need cooldown[] to check notHold maxProfit for i-d-1 day
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
            int[][] dp = new int[n+1][2];
            dp[0][1] = Integer.MIN_VALUE / 2;
            for (int i = 1; i <= n; i++) {
                int price = prices[i-1], prevSellIdx = Math.max(0, i-2);
                dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + price);
                dp[i][1] = Math.max(dp[i-1][1], dp[prevSellIdx][0] - price);
            }
            return dp[n][0];
        }
    }

    class Solution2_DP_SpaceOptimized {
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
    
    // We can use a queue with size as d+1 to reduce space complexity to O(d)
    class Followup_CooldownDDays {
        public int maxProfit(int[] prices, int d) {
            int n = prices.length;
            int notHold = 0, hold = Integer.MIN_VALUE / 2;
            int[] cooldown = new int[n];
            for (int i = 0; i < n; i++) {
                int price = prices[i], cooldownIdx = Math.max(0, i - d - 1);
                notHold = Math.max(notHold, hold + price);
                hold = Math.max(hold, cooldown[cooldownIdx] - price);
                cooldown[i] = notHold;
            }
            return notHold;
        }
    }
}
