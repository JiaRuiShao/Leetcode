/**
 * 55. Jump Game
 * 
 * Clarification:
 * - Range of nums[i]? >=0 non-negative, no backward jump
 * 
 * Corner Cases:
 * - [0] true
 */
public class _55 {
    class Solution1_Greedy {
        public boolean canJump(int[] nums) {
            int farthest = 0;
            for (int i = 0; i < nums.length - 1; i++) { // [0, n-2]
                farthest = Math.max(farthest, i + nums[i]);
                if (farthest <= i) return false;
            }
            return true;
        }
    }

    // TLE
    class Solution0_Recursion_TLE {
        public boolean canJump(int[] nums) {
            return dfs(nums, 0, nums.length - 1);
        }
    
        private boolean dfs(int[] nums, int start, int end) {
            if (start == end) {
                return true;
            }
            for (int pos = 1; pos <= nums[start]; pos++) {
                if (dfs(nums, start + pos, end)) {
                    return true;
                }
            }
            return false;
        }
    }

    class Solution2_Recursion_WithMemo {
        public boolean canJump(int[] nums) {
            int[] canJump = new int[nums.length]; // 0 means unknown, 1 means reachable, 2 means unreachable
            return dfs(nums, 0, nums.length - 1, canJump);
        }
    
        private boolean dfs(int[] nums, int start, int end, int[] cache) {
            if (start == end) {
                return true;
            }
            if (cache[start] != 0) return cache[start] == 1;
            for (int pos = 1; pos <= nums[start]; pos++) {
                if (dfs(nums, start + pos, end, cache)) {
                    cache[start] = 1;
                    return true;
                }
            }
            cache[start] = 2;
            return false;
        }
    }

    // Time: O(nk)
    // Space: O(n)
    class Solution3_DP {
        public boolean canJump(int[] nums) {
            int n = nums.length;
            boolean[] dp = new boolean[n]; // can reach n-1 from i
            dp[n-1] = true;
            for (int i = n - 2; i >= 0; i--) {
                for (int j = 1; j <= nums[i] && i + j < n; j++) {
                    dp[i] = dp[i] || dp[i+j];
                }
            }
            return dp[0];
        }
    }
}
