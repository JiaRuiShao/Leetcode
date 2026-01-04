/**
 * 213. House Robber II
 * 
 * Followup:
 * - k circular blocks of houses?
 */
public class _213 {
    // Time: O(n)
    // Space: O(1)
    class Solution1_DP_TwoPass {
        public int rob(int[] nums) {
            int n = nums.length;
            if (n == 1) return nums[0];
            return Math.max(robInRange(nums, 0, n - 2), robInRange(nums, 1, n - 1));
        }

        private int robInRange(int[] nums, int start, int end) {
            int prev2 = 0, prev1 = 0;
            for (int i = start; i <= end; i++) {
                int curr = Math.max(prev2 + nums[i], prev1);
                prev2 = prev1;
                prev1 = curr;
            }
            return prev1;
        }
    }

    class Followup_KBlocks {
        public int robKCircularBlocks(int[][] blocks) {
            // blocks[i] = array of houses in block i (circular)
            int total = 0;
            
            // Each block is independent - rob each optimally
            for (int[] block : blocks) {
                total += robCircular(block);
            }
            
            return total;
        }

        private int robCircular(int[] nums) {
            // same logic as above
            return 0;
        }
    }
}
