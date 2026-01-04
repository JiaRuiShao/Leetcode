/**
 * 121. Best Time to Buy and Sell Stock
 * 
 * - S1: BF nested loop O(n^2), O(1)
 * - S2: Recursion With Memo O(n), O(n)
 * - S3: Bottom-Up DP O(n), O(n)/O(1) [PREFERRED]
 * - S4: Greedy Sliding Window O(n), O(1) [PREFERRED]
 * 
 * Clarification:
 * - One transaction only? Yes
 * - Buy before sell only? Yes
 * - Range of prices[i]? 0 ≤ prices[i] ≤ 10^4
 * 
 * Followup:
 * - Transactions are unlimited? LC 122
 * - At most 2 transactions? LC 123
 * - At most k transactions? LC 188
 * - 1 day cooldown after selling? LC 309
 * - Transaction fee? LC 714
 * - Return actual buy & sell day, not just the profit?
 * - Short sell (sell before buying)
 * - Must buy exactly one stock? initialize maxProfit = -prices[0] instead of 0
 */
public class _121 {
    class Solution0_BruteForce_TLE {
        public int maxProfit(int[] prices) {
            int n = prices.length, maxProfit = 0;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    int profit = prices[j] - prices[i];
                    maxProfit = Math.max(maxProfit, profit);
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

    // max(profit) = max(prices[j] - prices[i]) where j > i ==> for each j, find min(prices[i])
    class Solution1_Greedy {
        public int maxProfit(int[] prices) {
            int minPrice = prices[0], maxProfit = 0;
            for (int price : prices) {
                maxProfit = Math.max(maxProfit, price - minPrice);
                minPrice = Math.min(minPrice, price);
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
            int hold = -prices[0] - 1;
            for (int i = 1; i <= n; i++) {
                notHold = Math.max(notHold, hold + prices[i - 1]);
                hold = Math.max(hold, 0 - prices[i - 1]);
            }
            return notHold;
        }
    }

    class Solution4_KadaneAlgo {
        // prices = [7, 1, 5, 3, 6, 4]
        // differences = [-6, 4, -2, 3, -2]
        // Find max sum subarray of diff array: [4, -2, 3] = 5
        public int maxProfit(int[] prices) {
            int maxProfit = 0;
            int maxEndingHere = 0;
            
            for (int i = 1; i < prices.length; i++) {
                maxEndingHere = Math.max(0, maxEndingHere + prices[i] - prices[i - 1]);
                maxProfit = Math.max(maxProfit, maxEndingHere);
            }
            
            return maxProfit;
        }
    }

    // Time: O(n log n)
    // Space: O(logn)
    class Solution5_DivideAndConquer {
        public int maxProfit(int[] prices) {
            return maxProfitHelper(prices, 0, prices.length - 1);
        }

        private int maxProfitHelper(int[] prices, int left, int right) {
            if (left >= right) return 0;
            
            int mid = left + (right - left) / 2;
            
            // Max profit in left half
            int leftMax = maxProfitHelper(prices, left, mid);
            
            // Max profit in right half
            int rightMax = maxProfitHelper(prices, mid + 1, right);
            
            // Max profit crossing middle (buy in left, sell in right)
            int minLeft = Integer.MAX_VALUE;
            for (int i = left; i <= mid; i++) {
                minLeft = Math.min(minLeft, prices[i]);
            }
            
            int maxRight = Integer.MIN_VALUE;
            for (int i = mid + 1; i <= right; i++) {
                maxRight = Math.max(maxRight, prices[i]);
            }
            
            int crossMax = maxRight - minLeft;
            
            return Math.max(Math.max(leftMax, rightMax), crossMax);
        }
    }

    class Followup_ReturnActualDays {
        public int[] maxProfitDays(int[] prices) {
            int minPrice = Integer.MAX_VALUE;
            int maxProfit = 0;
            int buyDay = 0;
            int sellDay = 0;
            int tempBuyDay = 0;
            
            for (int i = 0; i < prices.length; i++) {
                if (prices[i] < minPrice) {
                    minPrice = prices[i];
                    tempBuyDay = i;
                }
                
                int profit = prices[i] - minPrice;
                if (profit > maxProfit) {
                    maxProfit = profit;
                    buyDay = tempBuyDay;
                    sellDay = i;
                }
            }
            
            return new int[]{buyDay, sellDay, maxProfit};
        }
    }

    class Followup_ShortSell {
        public int maxProfitShortSell(int[] prices) {
            int maxPrice = prices[0];
            int minPrice = prices[0];
            
            for (int price : prices) {
                maxPrice = Math.max(maxPrice, price);
                minPrice = Math.min(minPrice, price);
            }
            
            return maxPrice - minPrice;
        }
    }
}
