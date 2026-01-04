/**
 * 122. Best Time to Buy and Sell Stock II
 * 
 * Followup:
 * - What if we cannot buy & sell on the same day?
 */
public class _122 {
    class Solution1_Greedy {
        // max(profit) = max(prices[j] - prices[i]) where j > i ==> for each j, find min(prices[i])
        public int maxProfit(int[] prices) {
            int minPrice = prices[0], profit = 0;
            for (int price : prices) {
                if (price >= minPrice) {
                    profit += price - minPrice;
                }
                minPrice = price;
            }
            return profit;
        }
    }

    class Solution2_DP {
        public int maxProfit(int[] prices) {
            int n = prices.length;
            // dp[i][0/1] maxProfit not holding / holding a stock on day i-1
            int[][] dp = new int[n+1][2];
            dp[0][0] = 0;
            dp[0][1] = -prices[0] - 1;
            for (int i = 1; i <= n; i++) {
                int price = prices[i-1];
                dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + price);
                dp[i][1] = Math.max(dp[i-1][1], dp[i][0] - price); 
                // can use dp[i][0] instead of dp[i-1][0] because we CAN sell & buy on same day
            }
            return dp[n][0];
        }

        public int maxProfit2(int[] prices) {
            int n = prices.length;
            // dp[i][0/1] maxProfit not holding / holding a stock on day i
            int[][] dp = new int[n][2];
            
            // Day 0
            dp[0][0] = 0;          // Don't buy
            dp[0][1] = -prices[0]; // Buy on day 0 ✓ Makes sense!
            
            for (int i = 1; i < n; i++) {
                dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i]);
                dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0] - prices[i]);
            }
            
            return dp[n-1][0];
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

    // "No Same-Day Trading" requirement doesn't seem to effect the final profit
    class Followup_CantBuyAndSellOnSameDay {
        public int maxProfit_Greedy(int[] prices) {
            int profit = 0;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] > prices[i-1]) {
                    profit += prices[i] - prices[i-1];
                }
            }
            return profit;
        }

        public int maxProfit_DP(int[] prices) {
            int hold = -prices[0] - 1;
            int notHold = 0;
            
            for (int i = 1; i < prices.length; i++) {
                int temp = notHold; // save yesterday's notHold
                notHold = Math.max(notHold, hold + prices[i]);
                hold = Math.max(hold, temp - prices[i]); // use yesterday's sold
            }
            
            return notHold;
        }
    }
}
