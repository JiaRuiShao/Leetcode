import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 518. Coin Change II
 * 
 * Coins outer + Forward iteration = Combinations ✓
 * Amount outer + Forward iteration = Permutations ✗
 * 
 * Clarification:
 * - Can amount be 0? Yes return 1 as there's one way to use no coins
 * - Assume amount is non-negative?
 * - Assume coins be positive?
 * - Assume no duplicate coins?
 * - What's the range?
 * - What to return if not able to make amount? Return 0 not -1
 * 
 * Followup:
 * - Solve using 2D array
 * - Why 1D array works?
 * We only need combination # for last amount and current amount
 * 
 * - Why iterate coins in outer loop and amount in inner loop?
 * This is to avoid permutation counting -- we process each coin completely before moving to next
// CORRECT - Combinations (coins outer, amount inner)
for (int coin : coins) {
    for (int i = coin; i <= amount; i++) {
        dp[i] += dp[i - coin];
    }
}

// WRONG - Permutations (amount outer, coins inner)
for (int i = 1; i <= amount; i++) {
    for (int coin : coins) {
        if (coin <= i) {
            dp[i] += dp[i - coin];
        }
    }
}
 * - What if each coin can be used once? (0/1 Knapsack)?
 * - Return all combinations instead of combination count?
 * - Solve using recursion with memo?
 */
public class _518 {
    // Time: O(mn)
    // Space: O(m)
    class Solution1_DP_1D {
        //dp[i] = SUM(dp[i-coin]) for coins
        public int change(int amount, int[] coins) {
            int[] dp = new int[amount + 1]; // dp[i] = combination # that make amount i
            dp[0] = 1;
            for (int coin : coins) {
                for (int n = coin; n <= amount; n++) {
                    dp[n] += dp[n - coin]; // n >= coin
                }
            }
            return dp[amount];
        }
    }

    // Time: O(mn)
    // Space: O(mn)
    class Solution2_DP_2D {
        // dp[i][j] = combination # that make amount j using first i coins
        // dp[i][j] = dp[i-1][j]                    // don't use coin i
        //            + dp[i][j - coins[i-1]]         // use coin i (can reuse); here it's dp[i][j-coin] and not dp[i-1][j-coin] because coins can be used unlimited times
        public int change(int amount, int[] coins) {
            int n = coins.length;
            int[][] dp = new int[n + 1][amount + 1]; // dp[i][j] = ways to make amount j using first i coins
            Arrays.fill(dp[0], 0);
            for (int i = 0; i <= n; i++) {
                dp[i][0] = 1;
            }

            // Base case: one way to make amount 0 (use no coins)
            for (int i = 1; i <= n; i++) {
                int coin = coins[i-1];
                for (int j = 1; j <= amount; j++) {
                    // don't use ith coin
                    dp[i][j] = dp[i-1][j];
                    // use ith coin (coin unlimited)
                    if (j >= coin) dp[i][j] += dp[i][j-coin];
                }
                /* 
                * it's wrong to start j from coin since dp[i][j-coin] might not be calculated
                for (int j = coin; j <= amount; j++) { 
                    dp[i][j] = dp[i-1][j] + dp[i][j-coin];
                } */
            }
            
            return dp[n][amount];
        }
    }

    // Notice how forward iteration work for unlimited coins and backward for limited
    // i.e. coin = 1, amount = 3, when iterate forward, dp[3] = 1 but it should be 0 instead
    class Followup_CoinsAreLimited {
        public int change(int amount, int[] coins) {
            int[] dp = new int[amount + 1]; // dp[i] = combination # that make amount i
            dp[0] = 1;
            for (int coin : coins) {
                // MUST iterate *backwards* for 0/1 knapsack
                for (int n = amount; n >= coin; n--) {
                    dp[n] += dp[n - coin];
                }
            }
            return dp[amount];
        }
    }

    class Followup_ReturnCombinations {
        public List<List<Integer>> getCombinations(int amount, int[] coins) {
            List<List<Integer>> result = new ArrayList<>();
            backtrack(coins, amount, 0, new ArrayList<>(), result);
            return result;
        }

        private void backtrack(int[] coins, int remaining, int start, 
                            List<Integer> current, List<List<Integer>> result) {
            if (remaining == 0) {
                result.add(new ArrayList<>(current));
                return;
            }
            
            // Start from 'start' to avoid duplicates ([1,2] vs [2,1])
            for (int i = start; i < coins.length; i++) {
                if (coins[i] <= remaining) {
                    current.add(coins[i]);
                    // Pass 'i' not 'i+1' because we can reuse same coin
                    backtrack(coins, remaining - coins[i], i, current, result);
                    current.remove(current.size() - 1);
                }
            }
        }
    }

    class Followup_RecursionWithMemo {
        public int change(int amount, int[] coins) {
            Integer[][] memo = new Integer[coins.length][amount + 1];
            return helper(coins, amount, 0, memo);
        }

        private int helper(int[] coins, int amount, int idx, Integer[][] memo) {
            if (amount == 0) return 1;
            if (idx >= coins.length) return 0;
            if (memo[idx][amount] != null) return memo[idx][amount];
            
            int ways = 0;
            // Try using current coin 0, 1, 2, ... times
            for (int i = 0; i * coins[idx] <= amount; i++) {
                ways += helper(coins, amount - i * coins[idx], idx + 1, memo);
            }
            
            memo[idx][amount] = ways;
            return ways;
        }
    }
}
