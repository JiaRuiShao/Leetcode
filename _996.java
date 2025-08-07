import java.util.Arrays;

/**
 * 996. Number of Squareful Arrays
 */
public class _996 {
    // Time: O(n*n!)
    // Space: O(logn + n)
    class Solution1_Backtrack_Permutation_ElemUsedOnce_Dedup {
        private int squareCount;
        public int numSquarefulPerms(int[] nums) {
            squareCount = 0;
            Arrays.sort(nums);
            backtrack(nums, 0, 0, -1);
            return squareCount;
        }

        private void backtrack(int[] nums, int visited, int used, int prev) {
            if (used == nums.length) {
                squareCount++;
                return;
            }

            for (int i = 0; i < nums.length; i++) {
                if (((visited >> i) & 1) == 1 || (i > 0 && nums[i] == nums[i - 1] && ((visited >> (i - 1)) & 1) == 0) || !isPerfectSquare(prev, nums[i])) {
                    continue;
                }
                visited ^= (1 << i);
                backtrack(nums, visited, used + 1, nums[i]);
                visited ^= (1 << i);
            }
        }

        private boolean isPerfectSquare(int prev, int curr) {
            if (prev == -1) return true;
            long sum = prev + curr;
            long sq = (long) Math.sqrt(sum);
            return sq * sq == sum;
        }
    }
}
