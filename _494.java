import java.util.HashMap;
import java.util.Map;

/**
 * 494. Target Sum
 * 
 * This is a 0/1 Knapsack counting DP problem.
 * 
 * Clarification:
 * - What to return if impossible to reach target? Return 0
 * - Can num be 0? Yes
 * 
 * Followup:
 * - What if we can use each number multiple times? Unbounded DP; Use forward iteration
 */
public class _494 {
    // Time: O(2^n)
    // Space: O(n)
    class Solution1_Recursion_Backtrack {
        public int findTargetSumWays(int[] nums, int target) {
            return backtrack(nums, target, 0);
        }

        private int backtrack(int[] nums, int target, int i) {        
            // base case
            if (i == nums.length) {
                if (target == 0) return 1;
                else return 0;
            }

            int count = 0, num = nums[i];
            // add +
            count += backtrack(nums, target - num, i + 1);
            // add -
            count += backtrack(nums, target + num, i + 1);
            return count;
        }
    }

    // Time: O(nk) where k is sum(nums)
    // Space: O(nk)
    class Solution1_Recursion_BacktrackWithMemo {
        public int findTargetSumWays(int[] nums, int target) {
            Map<String, Integer> memo = new HashMap<>();
            return backtrack(nums, target, 0, memo);
        }

        private int backtrack(int[] nums, int target, int i, Map<String, Integer> memo) {        
            // base case
            if (i == nums.length) {
                if (target == 0) return 1;
                else return 0;
            }

            String key = i + "-" + target;
            if (memo.containsKey(key)) {
                return memo.get(key);
            }

            int count = 0, num = nums[i];
            // add +
            count += backtrack(nums, target - num, i + 1, memo);
            // add -
            count += backtrack(nums, target + num, i + 1, memo);
            memo.put(key, count);
            return count;
        }
    }

    class Solution1_DP_1D {
        // pos + neg = target ==> pos + -(total - pos) = target ==> 2*pos = target + total
        // ==> find subset with sum as (target + total) / 2
        // each elem used once -> 0/1 knapsack
        public int findTargetSumWays(int[] nums, int target) {
            int total = target;
            for (int num : nums) {
                total += num;
            }
            if (total < 0 || total % 2 != 0) return 0;
            int k = total / 2;
            int[] dp = new int[k + 1]; // subset count with sum as i
            dp[0] = 1; // one way to make 0: use nothing
            for (int num : nums) {
                for (int i = k; i >= num; i--) {
                    dp[i] += dp[i - num];
                }
            }
            return dp[k];
        }
    }

    class Solution2_DP_2D {
        public int findTargetSumWays(int[] nums, int target) {
            int total = target;
            for (int num : nums) {
                total += num;
            }
            if (total < 0 || total % 2 != 0) return 0;
            int k = total / 2, n = nums.length;
            int[][] dp = new int[n + 1][k + 1]; // number of ways to make sum j using first i elements
            dp[0][0] = 1;
            // dp[...][0] = 1 is wrong for this question because elem i can be 0 so it's not impossible

            for (int i = 1; i <= n; i++) {
                int num = nums[i - 1];
                // NOTICE: start from 0 and not 1 like in coin change and other knapsack problems because nums can contain zeros
                // if j start from 1, then zeros are not counted correctly, i.e. nums = [0], target = 0
                // res should be 2; but we would return 1 instead
                for (int j = 0; j <= k; j++) {
                    dp[i][j] = dp[i - 1][j]; 
                    if (j >= num) {
                        dp[i][j] += dp[i - 1][j - num];
                    }
                }
            }
            return dp[n][k];
        }
    }

    class Followup_ElemUsedUnlimited {
        // Unbounded version - forward iteration
        public int findTargetSumWaysUnbounded(int[] nums, int target) {
            int sum = 0;
            for (int num : nums) sum += num;
            
            if (sum < Math.abs(target) || (sum + target) % 2 != 0) {
                return 0;
            }
            
            int subsetSum = (sum + target) / 2;
            int[] dp = new int[subsetSum + 1];
            dp[0] = 1;
            
            for (int num : nums) {
                // Forward iteration allows reuse
                for (int j = num; j <= subsetSum; j++) {
                    dp[j] += dp[j - num];
                }
            }
            
            return dp[subsetSum];
        }
    }
}
