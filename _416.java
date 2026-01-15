import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * 416. Partition Equal Subset Sum
 * 
 * This is a 0/1 Knapsack Problem, like coin limited variant of LC 518
 * - each num used at most once
 * - capacity is target sum
 * - exists achieve target
 * 
 * Starts with 2D approach, then optimize into 1D array.
 * 
 * Followup:
 * - Return the actual partition subsets? Use backtrack or 2D DP
 * - Partition into K subsets? LC 698 use backtrack
 * - What if elements can be used multiple times? Unbounded Knapsack; change target inner iteration to forward
 */
public class _416 {
    // Time: O(nk)
    // Space: O(k)
    class Solution1_DP_1D {
        // exist a subset with sum as totalSum / 2
        public boolean canPartition(int[] nums) {
            int total = Arrays.stream(nums).sum();
            if (total % 2 != 0) return false;
            int target = total / 2;
            boolean[] dp = new boolean[target + 1];
            dp[0] = true;
            for (int num : nums) {
                for (int i = target; i >= num; i--) { // backward to avoid elem used more than once
                    dp[i] = dp[i] || dp[i - num];
                }
            }
            return dp[target];
        }
    }

    // dp[i][j] = if we can make subset sum j using first i elem in nums
    // dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i-1]]
    // Time: O(nk) where k = sum / 2
    // Space: O(nk)
    class Solution2_DP_2D {
        // can we form a subset with sum = target?
        public boolean canPartition(int[] nums) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }
            if (sum % 2 != 0) {
                return false;
            }
            sum /= 2;
            int n = nums.length;
            // dp[i][j] = if we can make subset sum j using first i elem in nums
            boolean[][] dp = new boolean[n + 1][sum + 1];
            // dp[i][0] = true
            // dp[0][1..j] = false
            for (int i = 0; i <= n; i++) {
                dp[i][0] = true;
            }
            // dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i-1]]
            for (int i = 1; i <= n; i++) {
                int num = nums[i - 1];
                for (int j = 1; j <= sum; j++) {
                    dp[i][j] = dp[i - 1][j];
                    if (j >= num) {
                        dp[i][j] |= dp[i - 1][j - num];
                    }
                }
            }
            return dp[n][sum];
        }

        // Two ways to optimize 2D DP to 1D
        // 1 - save prev row (not efficient)
        public boolean canPartition_SpaceOptimized1(int[] nums) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }
            if (sum % 2 != 0) {
                return false;
            }
            sum /= 2;
            int n = nums.length;
            // dp[i][j] = if we can make subset sum j using first i elem in nums
            boolean[] prev = new boolean[sum + 1];
            // dp[i][0] = true
            // dp[0][1..j] = false
            for (int i = 0; i <= n; i++) {
                prev[0] = true;
            }
            // dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i-1]]
            for (int i = 1; i <= n; i++) {
                boolean[] curr = new boolean[sum + 1];
                curr[0] = true;
                int num = nums[i - 1];
                for (int j = 1; j <= sum; j++) {
                    curr[j] = prev[j];
                    if (j >= num) {
                        curr[j] = curr[j] || prev[j - num];
                    }
                }
                prev = curr;
            }
            return prev[sum];
        }

        // 2 - iterate from right to left
        public boolean canPartition_SpaceOptimized2(int[] nums) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }
            if (sum % 2 != 0) {
                return false;
            }
            int k = sum / 2;
            int n = nums.length;
            boolean[] dp = new boolean[k + 1];
            dp[0] = true;
            // dp[i][j] depends on dp[i-1][j] and dp[i-1][j-nums[i-1]]
            // we can iterate from right to left for inner loop iteration
            for (int i = 1; i <= n; i++) {
                int num = nums[i - 1];
                for (int j = k; j >= num; j--) {
                    dp[j] = dp[j] || dp[j - num];
                }
            }
            return dp[k];
        }
    }

    class Followup_ReturnSubsets {
        public boolean canPartition(int[] nums) {
            int n = nums.length, total = Arrays.stream(nums).sum();
            if (total % 2 != 0) return false;
            int target = total / 2;
            boolean[][] dp = new boolean[n + 1][target + 1]; // subset exist for sum j using first i num
            // dp[...][0] = true
            // others default value as false
            for (int i = 0; i <= n; i++) {
                Arrays.fill(dp[i], false);
                dp[i][0] = true;
            }

            for (int i = 1; i <= n; i++) {
                int num = nums[i - 1];
                for (int j = 1; j <= target; j++) {
                    dp[i][j] = dp[i - 1][j];
                    if (j >= num) dp[i][j] = dp[i][j] || dp[i - 1][j - num];
                }
            }
            
            if (!dp[n][target]) return false;
            
            // Backtrack to find the subset
            List<Integer> subset1 = new ArrayList<>();
            List<Integer> subset2 = new ArrayList<>();
            int i = n, j = target;
            while (i > 0 && j > 0) {
                // If dp[i][j] is true and dp[i-1][j] is false, 
                // then nums[i-1] was used
                int num = nums[i - 1];
                if (!dp[i-1][j]) {
                    subset1.add(num);
                    j -= num;
                } else {
                    subset2.add(num);
                }
                i--;
            }
            
            System.out.println("Subset 1: " + subset1);
            System.out.println("Subset 2: " + subset2);            
            return true;
        }
    }

    class Followup_ElemUsedUnlimited {
        public boolean canPartition(int[] nums) {
            int total = Arrays.stream(nums).sum();
            if (total % 2 != 0) return false;
            int target = total / 2;
            boolean[] dp = new boolean[target + 1];
            dp[0] = true;
            for (int num : nums) {
                for (int i = num; i <= target; i++) { // forward
                    dp[i] = dp[i] || dp[i - num];
                }
            }
            return dp[target];
        }

        public boolean canPartition2D(int[] nums) {
            int n = nums.length, total = Arrays.stream(nums).sum();
            if (total % 2 != 0) return false;
            int target = total / 2;
            boolean[][] dp = new boolean[n + 1][target + 1]; // subset exist for sum j using first i num
            // dp[...][0] = true
            // others default value as false
            for (int i = 0; i <= n; i++) {
                Arrays.fill(dp[i], false);
                dp[i][0] = true;
            }

            for (int i = 1; i <= n; i++) {
                int num = nums[i - 1];
                for (int j = 1; j <= target; j++) {
                    dp[i][j] = dp[i - 1][j];
                    if (j >= num) dp[i][j] = dp[i][j] || dp[i][j - num];
                }
            }
            return dp[n][target];
        }
    }

    // Time: O(k × 2^n)
    class Followup_KPartitions {
        public boolean canPartitionKSubsets(int[] nums, int k) {
            int sum = 0;
            for (int num : nums) sum += num;
            
            if (sum % k != 0) return false;
            int target = sum / k;
            
            // Use backtracking with memo
            boolean[] used = new boolean[nums.length];
            return backtrack(nums, used, 0, k, 0, target);
        }

        private boolean backtrack(int[] nums, boolean[] used, int start, 
                                int k, int currentSum, int target) {
            if (k == 1) return true; // Last subset automatically valid
            if (currentSum == target) {
                // Found one valid subset, look for remaining k-1 subsets
                return backtrack(nums, used, 0, k - 1, 0, target);
            }
            
            for (int i = start; i < nums.length; i++) {
                if (used[i] || currentSum + nums[i] > target) continue;
                
                used[i] = true;
                if (backtrack(nums, used, i + 1, k, currentSum + nums[i], target)) {
                    return true;
                }
                used[i] = false;
            }
            
            return false;
        }
    }
}
