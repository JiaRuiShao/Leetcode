import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1. Two Sum
 * 
 *  1 - BF: nested for loop O(n^2), O(1)
 *  2 - Sort + TP O(nlogn), O(n)
 *  3 - Map<Val, Idx> O(n), O(n)
 * 
 * Clarification:
 * - One solution?
 * - Each element used once?
 * - Return value or indices?
 * - Does order matter?
 * 
 * Followup:
 * - Input is sorted? Use two pointers with O(n) time and O(1) space
 * - What if you can't use extra space (no HashMap)? Brute Force O(n^2); Sort-in place + two pointers O(nlogn) still need extra O(n) space to store val to idx mappings unless the input nums is guaranteed to be sorted
 * - Multiple solutions: return all pairs
 * - Input is a stream? Store limited size of val, idx pairs; remove oldest when full
 * - Extend to Three Sum? LC 15 must sort for three sum
 * 
 * Red Flags:
 * - Use same elem twice
 * - Return val instead of indices
 * - Missing duplicate checking
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
            int[] indices = new int[2];
            Map<Integer, Integer> valToIdx = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                int num = nums[i], supplement = target - num;
                if (valToIdx.containsKey(supplement)) {
                    indices[0] = valToIdx.get(supplement);
                    indices[1] = i;
                    break;
                }
                valToIdx.putIfAbsent(num, i);
            }
            return indices;
        }
    }

    static class FollowupMultipleAnswers {
        public List<int[]> twoSums(int[] nums, int target) {
            List<int[]> pairs = new ArrayList<>();
            Map<Integer, List<Integer>> valToIndices = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                int num = nums[i], supplement = target - num;
                if (valToIndices.containsKey(supplement)) {
                    for (int idx : valToIndices.get(supplement)) {
                        pairs.add(new int[]{idx, i});
                    }
                }
                valToIndices.computeIfAbsent(num, k -> new ArrayList<>()).add(i);
            }
            return pairs;
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
