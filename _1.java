import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 1. Two Sum
 */
public class _1 {
    // assumptions: cannot use the same element twice
    // exact one solution
    // array is not sorted
    // i, j that f(i) + f(j) = target -> f(i) = target - f(j)
    class Solution1_Brute_Force {
        // Time: O(n^2)
        // Time: O(1)
        public int[] twoSum(int[] nums, int target) {
            if (nums == null || nums.length < 2) return new int[0];
            int n = nums.length;
            for (int i = 0; i < n; i++) {
                int num = nums[i], supplement = target - num;
                for (int j = i + 1; j < n; j++) {
                    if (nums[j] == supplement) {
                        return new int[]{i, j};
                    }
                }
            }
            return new int[0];
        }
    }

    static class Solution2_HashMap {
        // Time: O(n)
        // Space: O(n)
        public int[] twoSum(int[] nums, int target) {
            if (nums == null || nums.length < 2) return new int[0];
        
            Map<Integer, Integer> numToIndex = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                int complement = target - nums[i];
                Integer complementIndex = numToIndex.get(complement);
                if (complementIndex != null) {
                    return new int[]{complementIndex, i};
                }
                numToIndex.put(nums[i], i);
            }
            return new int[0];
        }
        
    }

    @Test
    public void testExampleCase() {
        Solution2_HashMap sol = new Solution2_HashMap();
        int[] result = sol.twoSum(new int[] {2, 7, 11, 15}, 9);
        Assert.assertArrayEquals(new int[] {0, 1}, result);
    }

    @Test
    public void testNoSolution() {
        Solution2_HashMap sol = new Solution2_HashMap();
        int[] result = sol.twoSum(new int[] {1, 2, 3}, 7);
        Assert.assertArrayEquals(new int[] {}, result);
    }

    @Test
    public void testDuplicateValues() {
        Solution2_HashMap sol = new Solution2_HashMap();
        int[] result = sol.twoSum(new int[] {3, 3}, 6);
        Assert.assertArrayEquals(new int[] {0, 1}, result);
    }

    @Test
    public void testNegativeNumbers() {
        Solution2_HashMap sol = new Solution2_HashMap();
        int[] result = sol.twoSum(new int[] {-1, -2, -3, -4}, -5);
        Assert.assertArrayEquals(new int[] {1, 2}, result);
    }
}
