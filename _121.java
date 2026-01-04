/**
 * 121. Best Time to Buy and Sell Stock
 * 
 * - S1: BF nested loop O(n^2), O(1)
 * - S2: Recursion With Memo O(n), O(n)
 * - S3: Bottom-Up DP O(n), O(n)/O(1)
 */
public class _121 {
    class Solution0_BruteForce_TLE {
        public int maxProfit(int[] prices) {
            int n = prices.length, maxProfit = 0;
            for (int i = n - 1; i >= 0; i--) {
                for (int j = i + 1; j < n; j++) {
                    if (prices[j] > prices[i]) maxProfit = Math.max(maxProfit, prices[j] - prices[i]);
                }
            }
            return maxProfit;
        }
    }

    class Solution0_BruteForce_DFS_TLE {
        public int maxProfit(int[] prices) {
            // private Map<String, Integer> memo = new HashMap<>();
            return dfs(prices, 0, false, 0);
        }
    
        private int dfs(int[] prices, int day, boolean holding, int buyPrice) {
            if (day == prices.length) return 0;
    
            // String key = day + "-" + holding + "-" + buyPrice;
            // if (memo.containsKey(key)) return memo.get(key);

            // Option 1: Skip today
            int profit = dfs(prices, day + 1, holding, buyPrice);
    
            // Option 2: Buy or sell depending on holding state
            if (!holding) {
                // Try buying
                profit = Math.max(profit, dfs(prices, day + 1, true, prices[day]));
            } else {
                // Try selling
                int currProfit = prices[day] - buyPrice;
                profit = Math.max(profit, currProfit);
            }
            
            // memo.put(key, profit);
            return profit;
        }
    }

    class Solution1_Two_Pointers {
        public int maxProfit(int[] prices) {
            int n = prices.length, profit = 0, maxProfit = 0;
            int left = 0, right = 0;
            while (++right < n) {
                profit = prices[right] - prices[left];
                if (profit > 0) maxProfit = Math.max(maxProfit, profit);
                else left = right;
            }
            return maxProfit;
        }
    }

    class Solution2_DP {
        public int maxProfit(int[] prices) {
            int n = prices.length;
            // dp[i][0/1]: max profit of not holding/holding a stock on day i - 1
            int[][] dp = new int[n + 1][2];
            dp[0][0] = 0;
            dp[0][1] = -10001;
            for (int i = 1; i <= n; i++) {
                dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i - 1]);
                // dp[i - 1][0]: didn't have stock yesterday and still don't
                // dp[i - 1][1] + prices[i - 1]: had stock yesterday and sell today
                dp[i][1] = Math.max(dp[i - 1][1], -prices[i - 1]);
                // dp[i - 1][1]: had stock yesterday and keep holding
                // -prices[i - 1]: buy stock today (first time buying, so profit = -price)
            }
            return dp[n][0];
        }
    }

    class Solution3_DP_SpaceOptimized {
        public int maxProfit(int[] prices) {
            int n = prices.length;
            int notHold = 0;
            int hold = -10001;
            for (int i = 1; i <= n; i++) {
                notHold = Math.max(notHold, hold + prices[i - 1]);
                hold = Math.max(hold, 0 - prices[i - 1]);
            }
            return notHold;
        }
    }
}
