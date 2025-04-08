/**
 * 121. Best Time to Buy and Sell Stock
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

    /**
     * [i, buy/sell] - here buy/sell will be represented using boolean 0/1
     * dp[i][0]: Max profit on day i when not holding a stock (sell)
     * dp[i][1]: Max profit on day i when holding a stock (buy / cool down)
     */
    class Solution2_DP {
        int maxProfit(int[] prices) {
            int n = prices.length;        
            int[][] dp = new int[n][2];
            for (int i = 0; i < n; i++) {
                if (i == 0) {
                    dp[i][0] = 0;             // No stock on day 0
                    dp[i][1] = -prices[i];    // Bought stock on day 0
                } else {
                    dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
                    dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
                }
            }
            return dp[n - 1][0];
        }
    }

    class Solution3_DP {
        int maxProfit(int[] prices) {
            int n = prices.length;        
            int[][] dp = new int[n + 1][2];
            dp[0][0] = 0; // base cases
            dp[0][1] = Integer.MIN_VALUE;
    
            for (int i = 0; i < n; i++) {
                int day = i + 1;
                dp[day][0] = Math.max(dp[day - 1][0], dp[day - 1][1] + prices[i]); // sell
                dp[day][1] = Math.max(dp[day - 1][1], -prices[i]); // buy
            }
            return dp[n][0];
        }
    }
}
