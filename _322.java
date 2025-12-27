import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 322. Coin Change
 * 
 * Clarification:
 * - Amount range?
 * - Can amount be 0? Yes
 * - Assume coins are not duplicated?
 * - What to return if it's impossible to make the amount? return -1
 * - Can we use the same coin multiple times? Yes
 * 
 * Followup:
 * - Can you explain why a greedy approach won't work? We always pick largest coin and local optimal ≠ global optimal, i.e. coins: [3, 4], amount = 6
 * - Explain your dp recurrence relation? dp[i] = minimum coins needed to make amount i; base case dp[0] = 0
 * recurrence: dp[i] = min(dp[i], 1+dp[i-coin]) for all coins
 * - Solve this question with both top-down and bottom-up approaches.
 * - Return the actual coins you used instead?
 * - Why do you initialize dp with amount + 1 instead of Integer.MAX_VALUE? here we need a larger impossible number
 * By using amount + 1 we can avoid integer overflow & the logic is simpler in that we don't need some more if elses to check for corner cases
 * - What if you want to find the number of different ways to make the amount? LC 518 This becomes unbounded knapsack Problem
 * - What if coins are limited (can only use each coin once)? This becomes 0/1 knapsack variant
 */
public class _322 {
    class Solution0_Greedy_Not_Work {
        // This greedy solution doesn't guarantee to work
        // i.e. coins: [3, 4], amount = 6
        public int coinChange(int[] coins, int amount) {
            Arrays.sort(coins);
            int coinUsed = 0;
            for (int i = coins.length - 1; i >= 0 && amount > 0; i--) {
                int coin = coins[i];
                if (amount >= coin) {
                    coinUsed += (amount / coin);
                    amount %= coin;
                }
            }
            return amount == 0 ? coinUsed : -1;
        }
    }

    /**
     * Top-Down Recursion Approach.
     * Time: (n^m) where n is coin number & m is amount number
     * Space: O(m)
     */
    class Solution2_Brute_Force_TLE {
        int coinUsed = Integer.MAX_VALUE;
    
        public int coinChange(int[] coins, int amount) {
            getCoins(coins, amount, 0);
            return coinUsed == Integer.MAX_VALUE ? -1 : coinUsed;
        }
    
        private void getCoins(int[] coins, int n, int used) {
            if (n == 0) {
                coinUsed = Math.min(coinUsed, used);
                return;
            }
            if (n < 0) return;
            for (int coin : coins) {
                getCoins(coins, n - coin, used + 1);
            }
        }
    }

    class Solution2_BF_TLE_WithoutGlobalVar {
        public int coinChange(int[] coins, int amount) {
            if (amount == 0) return 0;
            int coinsUsed = Integer.MAX_VALUE;
            for (int coin : coins) {
                if (coin <= amount) {
                    int used = coinChange(coins, amount - coin);
                    if  (used == -1) continue;
                    coinsUsed = Math.min(coinsUsed, 1 + used);
                }
            }
            return coinsUsed == Integer.MAX_VALUE ? -1 : coinsUsed;
        }
    }

    /**
     * Bottom-Up Recursion Approach with DP memo.
     * Time: O(mn)
     * Space: O(m) 
     */
    class Solution3_DP_Recursion {
        static final int INF = Integer.MAX_VALUE;
        
        public int coinChange(int[] coins, int amount) {
            int[] memo = new int[amount + 1]; // size  = amount + 1
            int minCoins = getCoins(coins, memo, amount);
            return minCoins == INF ? -1 : minCoins;
        }
    
        private int getCoins(int[] coins, int[] memo, int n) {
            if (n < 0) return INF;
            if (n == 0) return 0; // return 0 or 1 for base case
            if (memo[n] > 0) return memo[n];
    
            int minCoins = INF;
            for (int coin : coins) {
                minCoins = Math.min(minCoins, getCoins(coins, memo, n - coin));
            }
            minCoins = minCoins == INF ? INF : minCoins + 1;
            memo[n] = minCoins;
    
            return minCoins;
        }
    }

    /**
     * Bottom-Up Iterative Approach with DP memo.
     * Time: O(mn)
     * Space: O(m) 
     */
    class Solution4_DP_Iterative {
        static final int INF = Integer.MAX_VALUE;
        
        public int coinChange(int[] coins, int amount) {    
            int[] memo = new int[amount + 1]; // size  = amount + 1
            Arrays.fill(memo, INF);
            memo[0] = 0; // base case
            int currAmount = 0, leftAmount = 0;
    
            while (++currAmount <= amount) {
                for (int coin : coins) {
                    leftAmount = currAmount - coin;
                    if (leftAmount >= 0 && memo[leftAmount] != INF) {
                        memo[currAmount] = Math.min(memo[currAmount], memo[leftAmount] + 1);
                    }
                }
            }
    
            return memo[amount] == INF ? -1 : memo[amount];
        }
    }

    /**
     * Bottom-Up Iterative Approach with DP memo.
     * Time: O(mn)
     * Space: O(m) 
     */
    class Solution5_DP_Iterative_Improved {
        // f(n) = Math.min(1 + f(n-coin_i)) for coins[i]
        public int coinChange(int[] coins, int amount) {
            int[] memo = new int[amount + 1]; // coins used to make up to amount i from 0 to amount
            Arrays.fill(memo, amount + 1);
            memo[0] = 0;
            for (int n = 1; n <= amount; n++) {
                for (int coin : coins) {
                    int remaining = n - coin;
                    if (remaining < 0) continue;
                    memo[n] = Math.min(memo[n], 1 + memo[remaining]);
                }
            }
            return memo[amount] == amount + 1 ? -1 : memo[amount];
        }
    }

    class Followup_ReturnActualCoins {
        public int coinChange(int[] coins, int amount) {
            int[] dp = new int[amount + 1];
            int[] parent = new int[amount + 1]; // Track which coin was used
            Arrays.fill(dp, amount + 1);
            Arrays.fill(parent, -1);
            dp[0] = 0;
            
            for (int i = 1; i <= amount; i++) {
                for (int coin : coins) {
                    if (coin <= i && dp[i - coin] + 1 < dp[i]) {
                        dp[i] = dp[i - coin] + 1;
                        parent[i] = coin; // Remember this coin
                    }
                }
            }
            
            if (dp[amount] > amount) return -1;
            
            // Reconstruct solution
            List<Integer> result = new ArrayList<>();
            int curr = amount;
            while (curr > 0) {
                result.add(parent[curr]);
                curr -= parent[curr];
            }
            return dp[amount];
        }
    }

    /**
     * Complete Example Walkthrough: 0/1 Knapsack (Each Coin Used At Most Once)
        Input: coins = [1, 2, 5], amount = 6, each coin used at most once
        Expected answer: 2 (using coins 1 and 5)

        Initialization:
        dp = [0, ∞, ∞, ∞, ∞, ∞, ∞]
            0  1  2  3  4  5  6

        Processing coin = 1 (value = 1)
        Iterate backwards from 6 to 1:

        i = 6: dp[6] = min(∞, dp[5] + 1) = ∞
        i = 5: dp[5] = min(∞, dp[4] + 1) = ∞
        i = 4: dp[4] = min(∞, dp[3] + 1) = ∞
        i = 3: dp[3] = min(∞, dp[2] + 1) = ∞
        i = 2: dp[2] = min(∞, dp[1] + 1) = ∞
        i = 1: dp[1] = min(∞, dp[0] + 1) = 1  ✓

        Result: dp = [0, 1, ∞, ∞, ∞, ∞, ∞]

        Processing coin = 2 (value = 2)
        Iterate backwards from 6 to 2:

        i = 6: dp[6] = min(∞, dp[4] + 1) = ∞
        i = 5: dp[5] = min(∞, dp[3] + 1) = ∞
        i = 4: dp[4] = min(∞, dp[2] + 1) = ∞
        i = 3: dp[3] = min(∞, dp[1] + 1) = min(∞, 1+1) = 2  ✓ (coin 1 + coin 2)
        i = 2: dp[2] = min(∞, dp[0] + 1) = 1  ✓ (just coin 2)

        Result: dp = [0, 1, 1, 2, ∞, ∞, ∞]

        Processing coin = 5 (value = 5)
        Iterate backwards from 6 to 5:

        i = 6: dp[6] = min(∞, dp[1] + 1) = min(∞, 1+1) = 2  ✓ (coin 1 + coin 5)
        i = 5: dp[5] = min(∞, dp[0] + 1) = 1  ✓ (just coin 5)

        Result: dp = [0, 1, 1, 2, ∞, ∞, 2]

        Final answer: dp[6] = 2 ✓

        Why Backward Iteration is Critical:
        - When processing dp[i], we need dp[i - coin] to be from the PREVIOUS iteration
        - Backward iteration ensures dp[i - coin] hasn't been updated yet in current iteration
        - This prevents reusing the same coin multiple times
        - Forward iteration would update dp[2] first, then dp[4] would see the updated dp[2],
        causing coin 2 to be used twice (once for dp[2], again for dp[4])
     */
    class Followup_CoinsAreLimited {
        public int coinChange(int[] coins, int amount) {
            int[] dp = new int[amount + 1];
            Arrays.fill(dp, amount + 1);
            dp[0] = 0;
            
            for (int coin : coins) {
                // Iterate backwards to avoid using same coin twice
                for (int i = amount; i >= coin; i--) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
            
            return dp[amount] > amount ? -1 : dp[amount];
        }
    }
}
