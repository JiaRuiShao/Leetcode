/**
 * 55. Jump Game
 */
public class _55 {
    class Solution1_Greedy {
        public boolean canJump(int[] nums) {
            int n = nums.length, jump = 0;
            for (int pos = 0; pos < n - 1; pos++) {
                jump = Math.max(jump, pos + nums[pos]);
                if (jump <= pos) {
                    return false;
                }
            }
            return jump >= n - 1;
        }
    }

    // TLE
    class Solution2_DP_Top_Down {
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

    class Solution3_DP_Top_Down_With_Memo {
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

    class Solution4_Dp_Iterative_Bottom_Up {
        public boolean canJump(int[] nums) {
            int n = nums.length;
            int[] canJump = new int[n]; // 0 means unreachable, 1 means reachable
            canJump[n - 1] = 1;
            for (int pos = n - 2; pos >= 0; pos--) {
                int reach = Math.min(pos + nums[pos], n - 1);
                for (int jump = pos + 1; jump <= reach; jump++) {
                    if (canJump[jump] == 1) {
                        canJump[pos] = 1;
                        break;
                    }
                }
            }
            return canJump[0] == 1;
        }
    }
}
