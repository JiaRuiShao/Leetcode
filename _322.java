import java.util.Arrays;

/**
 * 322. Coin Change
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
            if (amount == 0) return 0;
    
            int[] memo = new int[amount + 1]; // size  = amount + 1
            Arrays.fill(memo, INF);
            int currAmount = 0, leftAmount = 0;
    
            while (++currAmount <= amount) {
                for (int coin : coins) {
                    leftAmount = currAmount - coin;
                    if (leftAmount == 0) {
                        memo[currAmount] = 1;
                    } else if (leftAmount > 0 && memo[leftAmount] != INF) {
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
        public int coinChange(int[] coins, int amount) {
            int[] memo = new int[amount + 1]; // size  = amount + 1
            Arrays.fill(memo, amount + 1); // didn't use INF to prevent integer overflow
            memo[0] = 0; // change base case from 1 to 0
    
            int currAmount = 0, leftAmount = 0;
            while (++currAmount <= amount) {
                for (int coin : coins) {
                    leftAmount = currAmount - coin;
                    if (leftAmount < 0) {
                        continue;
                    }
                    memo[currAmount] = Math.min(memo[currAmount], memo[leftAmount] + 1); // memo[leftAmount] + 1 will not cause integer overflow since we didn't initialize all original value as Integer.MAX_VALUE
                }
            }
    
            return memo[amount] == (amount + 1) ? -1 : memo[amount];
        }
    }
}
